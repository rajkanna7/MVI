package com.rk.imagepicker.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.rk.imagepicker.constant.AssetPickerConfig
import com.rk.imagepicker.data.AssetInfo
import com.rk.imagepicker.data.AssetPickerRepository
import com.rk.imagepicker.data.AssetPickerRoute
import com.rk.imagepicker.data.AssetViewModel
import com.rk.imagepicker.data.AssetViewModelFactory

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AssetPicker(
    assetPickerConfig: AssetPickerConfig,
    onPicked: (List<AssetInfo>) -> Unit,
    onClose: (List<AssetInfo>) -> Unit,
) {
    val context = LocalContext.current
    val navController = rememberAnimatedNavController()
    val viewModel: AssetViewModel = viewModel(
        factory = AssetViewModelFactory(
            assetPickerRepository = AssetPickerRepository(context),
            assetPickerConfig = assetPickerConfig,
            navController = navController
        )
    )

    LaunchedEffect(Unit, block = {
        viewModel.initDirectories()
    })

    CompositionLocalProvider(LocalAssetConfig provides assetPickerConfig) {
        AssetPickerRoute(
            navController = navController,
            viewModel = viewModel,
            onPicked = onPicked,
            onClose = onClose,
        )
    }
}
