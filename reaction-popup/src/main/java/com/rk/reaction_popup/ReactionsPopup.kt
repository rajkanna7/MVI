package com.rk.reaction_popup

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.EmojiView
import com.vanniktech.emoji.google.GoogleEmojiProvider


@Composable
fun ReactionOption(
    onDismissRequest: () -> Unit,
    onReactionPressed: (Emoji) -> Unit,
    offset: IntOffset = IntOffset.Zero
) {

    var isShowReaction by remember {
        mutableStateOf(true)
    }
    var isCustomReaction by remember {
        mutableStateOf(false)
    }
    if (isShowReaction) {
        ReactionsPopup(
            onDismissRequest = {
                isShowReaction = false
                onDismissRequest()
            },
            onReactionPressed,
            onNewReactionPressed={
                isShowReaction = false
                isCustomReaction=true
            },
            offset,
        )
    }
    if(isCustomReaction){
        CustomReaction(onReactionPressed)
    }


}


@Composable
fun ReactionsPopup(
    onDismissRequest: () -> Unit,
    onReactionPressed: (Emoji) -> Unit,
    onNewReactionPressed: () -> Unit,
    offset: IntOffset = IntOffset.Zero,
    properties: PopupProperties = PopupProperties(focusable = true),
) {
    Popup(
        offset = offset,
        properties = properties,
        onDismissRequest = onDismissRequest,
        content = {
            Reactions(
                favouriteEmojis = FavouriteEmojiProviderImpl().invoke(),
                onReactionPressed = onReactionPressed,
                onNewReactionPressed = onNewReactionPressed,
                onDismissRequest = onDismissRequest,
            )
        }
    )
}


@Composable
private fun Reactions(
    favouriteEmojis: List<Emoji>,
    onReactionPressed: (Emoji) -> Unit,
    onNewReactionPressed: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    var isShowReaction by remember {
        mutableStateOf(false)
    }
    Card(
        shape = CircleShape,
        backgroundColor = Color.White,
        modifier = Modifier.padding(4.dp)
    ) {
        val scaleEffect by animateFloatAsState(
            targetValue = if (isShowReaction) 1f else 0f,
            animationSpec = spring(0.35f, stiffness = 270f),
        )

        val rotationEffect by animateFloatAsState(
            targetValue = if (isShowReaction) 0f else -45f,
            animationSpec = spring(0.35f, stiffness = 270f)
        )

        val textOffset by animateDpAsState(
            targetValue = if (isShowReaction) 0.dp else (-15).dp,
            animationSpec = spring(0.35f, stiffness = 270f),
        )

        val textOffset2 by animateDpAsState(
            targetValue = if (isShowReaction) 0.dp else (15).dp,
            animationSpec = spring(0.35f, stiffness = 270f),
        )

        LaunchedEffect(true) {
            isShowReaction = !isShowReaction

        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(6.dp)
                .graphicsLayer(
                    scaleX = scaleEffect,
                    scaleY = scaleEffect,
                    transformOrigin = bottomTrailing()
                )
        ) {
            favouriteEmojis.forEach { emoji ->
                Text(
                    text = emoji.emoji,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .graphicsLayer(
                            scaleX = scaleEffect,
                            scaleY = scaleEffect,
                            transformOrigin = bottomTrailing()
                        )
                        .clickable {
                            onReactionPressed(emoji)
                            onDismissRequest()
                        }
                )
            }

            IconButton(onClick = {
               onNewReactionPressed()
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_plus_circle_neutral_500),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun CustomReaction(
    onReactionPressed: (Emoji) -> Unit,
) {
    EmojiManager.install(GoogleEmojiProvider())
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.weight(1f))
        Popup(
            onDismissRequest = {
            },
            alignment = Alignment.BottomCenter,
        ) {
            val view = LocalView.current
            AndroidView(
                factory = { context ->
                    EmojiView(context).apply {
                        setUp(
                            onEmojiClickListener = { emoji ->
                                onReactionPressed(EmojiModel(emoji.unicode))
                            },
                            rootView = view,
                            editText = null,
                            onEmojiBackspaceClickListener = {
                            }
                        )
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
        }
    }
}


@Composable
fun bottom() = TransformOrigin(0.5f, 1f)

@Composable
fun bottomTrailing() = TransformOrigin(1f, 1f)

@Composable
fun bottomLeading() = TransformOrigin(0.5f, 1f)

@Composable
fun topTrailing() = TransformOrigin(1f, 0f)