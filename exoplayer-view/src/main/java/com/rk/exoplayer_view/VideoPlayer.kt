/*
 * Copyright 2023 Dora Lee
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rk.exoplayer_view

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaSessionCompat
import android.widget.ImageButton
import androidx.activity.compose.BackHandler
import androidx.annotation.FloatRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.C.AUDIO_CONTENT_TYPE_MOVIE
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT
import com.google.android.exoplayer2.ui.R
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.util.RepeatModeUtil.REPEAT_TOGGLE_MODE_ALL
import com.google.android.exoplayer2.util.RepeatModeUtil.REPEAT_TOGGLE_MODE_NONE
import com.google.android.exoplayer2.util.RepeatModeUtil.REPEAT_TOGGLE_MODE_ONE
import com.rk.exoplayer_view.cache.VideoPlayerCacheManager
import com.rk.exoplayer_view.controller.VideoPlayerControllerConfig
import com.rk.exoplayer_view.controller.applyToExoPlayerView
import com.rk.exoplayer_view.pip.enterPIPMode
import com.rk.exoplayer_view.pip.isActivityStatePipMode
import com.rk.exoplayer_view.uri.VideoPlayerMediaItem
import com.rk.exoplayer_view.uri.toUri
import com.rk.exoplayer_view.util.findActivity
import com.rk.exoplayer_view.util.setFullScreen
import java.util.*
import kotlinx.coroutines.delay

/**
 * [VideoPlayer] is UI component that can play video in Jetpack Compose. It works based on ExoPlayer.
 * You can play local (e.g. asset files, resource files) files and all video files in the network environment.
 * For all video formats supported by the [VideoPlayer] component, see the ExoPlayer link below.
 *
 * If you rotate the screen, the default action is to reset the player state.
 * To prevent this happening, put the following options in the `android:configChanges` option of your app's AndroidManifest.xml to keep the settings.
 * ```
 * keyboard|keyboardHidden|orientation|screenSize|screenLayout|smallestScreenSize|uiMode
 * ```
 *
 * This component is linked with Compose [androidx.compose.runtime.DisposableEffect].
 * This means that it move out of the Composable Scope, the ExoPlayer instance will automatically be destroyed as well.
 *
 * @see <a href="https://exoplayer.dev/supported-formats.html">Exoplayer Support Formats</a>
 *
 * @param modifier Modifier to apply to this layout node.
 * @param mediaItems [VideoPlayerMediaItem] to be played by the video player. The reason for receiving media items as an array is to configure multi-track. If it's a single track, provide a single list (e.g. listOf(mediaItem)).
 * @param handleLifecycle Sets whether to automatically play/stop the player according to the activity lifecycle. Default is true.
 * @param autoPlay Autoplay when media item prepared. Default is true.
 * @param usePlayerController Using player controller. Default is true.
 * @param controllerConfig Player controller config. You can customize the Video Player Controller UI.
 * @param seekBeforeMilliSeconds The seek back increment, in milliseconds. Default is 10sec (10000ms). Read-only props (Changes in values do not take effect.)
 * @param seekAfterMilliSeconds The seek forward increment, in milliseconds. Default is 10sec (10000ms). Read-only props (Changes in values do not take effect.)
 * @param repeatMode Sets the content repeat mode.
 * @param volume Sets thie player volume. It's possible from 0.0 to 1.0.
 * @param onCurrentTimeChanged A callback that returned once every second for player current time when the player is playing.
 * @param fullScreenSecurePolicy Windows security settings to apply when full screen. Default is off. (For example, avoid screenshots that are not DRM-applied.)
 * @param onFullScreenEnter A callback that occurs when the player is full screen. (The [VideoPlayerControllerConfig.showFullScreenButton] must be true to trigger a callback.)
 * @param onFullScreenExit A callback that occurs when the full screen is turned off. (The [VideoPlayerControllerConfig.showFullScreenButton] must be true to trigger a callback.)
 * @param enablePip Enable PIP (Picture-in-Picture).
 * @param enablePipWhenBackPressed With [enablePip] is `true`, set whether to enable PIP mode even when you press Back. Default is false.
 * @param handleAudioFocus Set whether to handle the video playback control automatically when it is playing in PIP mode and media is played in another app. Default is true.
 * @param playerInstance Return exoplayer instance. This instance allows you to add [com.google.android.exoplayer2.analytics.AnalyticsListener] to receive various events from the player.
 */
@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    mediaItems: List<VideoPlayerMediaItem>,
    handleLifecycle: Boolean = true,
    autoPlay: Boolean = true,
    usePlayerController: Boolean = true,
    controllerConfig: VideoPlayerControllerConfig = VideoPlayerControllerConfig.Default,
    seekBeforeMilliSeconds: Long = 10000L,
    seekAfterMilliSeconds: Long = 10000L,
    repeatMode: RepeatMode = RepeatMode.NONE,
    @FloatRange(from = 0.0, to = 1.0) volume: Float = 1f,
    onCurrentTimeChanged: (Long) -> Unit = {},
    fullScreenSecurePolicy: SecureFlagPolicy = SecureFlagPolicy.Inherit,
    onFullScreenEnter: () -> Unit = {},
    onFullScreenExit: () -> Unit = {},
    enablePip: Boolean = false,
    enablePipWhenBackPressed: Boolean = false,
    handleAudioFocus: Boolean = true,
    playerInstance: ExoPlayer.() -> Unit = {},
) {
    val context = LocalContext.current
    var currentTime by remember { mutableStateOf(0L) }

    val player = remember {
        val httpDataSourceFactory = DefaultHttpDataSource.Factory()

        ExoPlayer.Builder(context)
            .setSeekBackIncrementMs(seekBeforeMilliSeconds)
            .setSeekForwardIncrementMs(seekAfterMilliSeconds)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AUDIO_CONTENT_TYPE_MOVIE)
                    .setUsage(C.USAGE_MEDIA)
                    .build(),
                handleAudioFocus,
            )
            .apply {
                val cache = VideoPlayerCacheManager.getCache()
                if (cache != null) {
                    val cacheDataSourceFactory = CacheDataSource.Factory()
                        .setCache(cache)
                        .setUpstreamDataSourceFactory(DefaultDataSource.Factory(context, httpDataSourceFactory))
                    setMediaSourceFactory(DefaultMediaSourceFactory(cacheDataSourceFactory))
                }
            }
            .build()
            .also(playerInstance)
    }

    val defaultPlayerView = remember {
        StyledPlayerView(context)
    }

    BackHandler(enablePip && enablePipWhenBackPressed) {
        enterPIPMode(context, defaultPlayerView)
        player.play()
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)

            if (currentTime != player.currentPosition) {
                onCurrentTimeChanged(currentTime)
            }

            currentTime = player.currentPosition
        }
    }

    LaunchedEffect(usePlayerController) {
        defaultPlayerView.useController = usePlayerController
    }

    LaunchedEffect(player) {
        defaultPlayerView.player = player
    }

    LaunchedEffect(mediaItems, player) {
        val mediaSession = MediaSessionCompat(
            context,
            "VideoPlayerMediaSession_${UUID.randomUUID().toString().lowercase().split("-").first()}",
        )
        val mediaSessionConnector = MediaSessionConnector(mediaSession)
        mediaSessionConnector.setPlayer(player)
        mediaSessionConnector.setQueueNavigator(
            object : TimelineQueueNavigator(mediaSession) {
                override fun getMediaDescription(player: Player, windowIndex: Int): MediaDescriptionCompat {
                    return MediaDescriptionCompat.Builder()
                        .setTitle(player.mediaMetadata.title)
                        .setDescription(player.mediaMetadata.description)
                        .build()
                }
            },
        )
        mediaSession.isActive = true

        val exoPlayerMediaItems = mediaItems.map {
            val uri = it.toUri(context)

            MediaItem.Builder().apply {
                setUri(uri)
                setMediaMetadata(it.mediaMetadata)
                setMimeType(it.mimeType)
                setDrmConfiguration(
                    if (it is VideoPlayerMediaItem.NetworkMediaItem) {
                        it.drmConfiguration
                    } else {
                        null
                    },
                )
            }.build()
        }

        player.setMediaItems(exoPlayerMediaItems)
        player.prepare()

        if (autoPlay) {
            player.play()
        }
    }

    var isFullScreenModeEntered by remember { mutableStateOf(false) }

    LaunchedEffect(controllerConfig) {
        controllerConfig.applyToExoPlayerView(defaultPlayerView) {
            isFullScreenModeEntered = it

            if (it) {
                onFullScreenEnter()
            }
        }
    }

    LaunchedEffect(controllerConfig, repeatMode) {
        defaultPlayerView.setRepeatToggleModes(
            if (controllerConfig.showRepeatModeButton) {
                REPEAT_TOGGLE_MODE_ALL or REPEAT_TOGGLE_MODE_ONE
            } else {
                REPEAT_TOGGLE_MODE_NONE
            },
        )
        player.repeatMode = repeatMode.toExoPlayerRepeatMode()
    }

    LaunchedEffect(volume) {
        player.volume = volume
    }

    VideoPlayerSurface(
        modifier = modifier,
        defaultPlayerView = defaultPlayerView,
        player = player,
        usePlayerController = usePlayerController,
        handleLifecycle = handleLifecycle,
        enablePip = enablePip,
    )

    if (isFullScreenModeEntered) {
        var fullScreenPlayerView by remember { mutableStateOf<StyledPlayerView?>(null) }

        VideoPlayerFullScreenDialog(
            player = player,
            currentPlayerView = defaultPlayerView,
            controllerConfig = controllerConfig,
            repeatMode = repeatMode,
            onDismissRequest = {
                fullScreenPlayerView?.let {
                    StyledPlayerView.switchTargetView(player, it, defaultPlayerView)
                    defaultPlayerView.findViewById<ImageButton>(R.id.exo_fullscreen)
                        .performClick()
                    val currentActivity = context.findActivity()
                    currentActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    currentActivity.setFullScreen(false)
                    onFullScreenExit()
                }

                isFullScreenModeEntered = false
            },
            securePolicy = fullScreenSecurePolicy,
            enablePip = enablePip,
            fullScreenPlayerView = {
                fullScreenPlayerView = this
            },
        )
    }
}

@Composable
internal fun VideoPlayerSurface(
    modifier: Modifier = Modifier,
    defaultPlayerView: StyledPlayerView,
    player: ExoPlayer,
    usePlayerController: Boolean,
    handleLifecycle: Boolean,
    enablePip: Boolean,
    onPipEntered: () -> Unit = {},
    autoDispose: Boolean = true,
) {
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
    val context = LocalContext.current

    var isPendingPipMode by remember { mutableStateOf(false) }

    DisposableEffect(
        AndroidView(
            modifier = modifier,
            factory = {
                defaultPlayerView.apply {
                    useController = usePlayerController
                    resizeMode = RESIZE_MODE_FIT
                    setBackgroundColor(Color.BLACK)
                }
            },
        ),
    ) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    if (handleLifecycle) {
                        player.pause()
                    }

                    if (enablePip && player.playWhenReady) {
                        isPendingPipMode = true

                        Handler(Looper.getMainLooper()).post {
                            enterPIPMode(context, defaultPlayerView)
                            onPipEntered()

                            Handler(Looper.getMainLooper()).postDelayed({
                                isPendingPipMode = false
                            }, 500)
                        }
                    }
                }

                Lifecycle.Event.ON_RESUME -> {
                    if (handleLifecycle) {
                        player.play()
                    }

                    if (enablePip && player.playWhenReady) {
                        defaultPlayerView.useController = usePlayerController
                    }
                }

                Lifecycle.Event.ON_STOP -> {
                    val isPipMode = context.isActivityStatePipMode()

                    if (handleLifecycle || (enablePip && isPipMode && !isPendingPipMode)) {
                        player.stop()
                    }
                }

                else -> {}
            }
        }
        val lifecycle = lifecycleOwner.value.lifecycle
        lifecycle.addObserver(observer)

        onDispose {
            if (autoDispose) {
                player.release()
                lifecycle.removeObserver(observer)
            }
        }
    }
}
