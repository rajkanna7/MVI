package com.rk.imagepicker.data

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument

import com.rk.imagepicker.constant.RequestType
import com.rk.imagepicker.view.AssetDisplayScreen
import com.rk.imagepicker.view.AssetPreviewScreen
import com.rk.imagepicker.view.AssetSelectorScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AssetPickerRoute(
    navController: NavHostController,
    viewModel: AssetViewModel,
    onPicked: (List<AssetInfo>) -> Unit,
    onClose: (List<AssetInfo>) -> Unit,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = "asset_display"
    ) {
        composable("asset_display") {
            AssetDisplayScreen(
                viewModel = viewModel,
                navigateToDropDown = { navController.navigate("asset_selector?directory=$it") },
                onPicked = onPicked,
                onClose = onClose,
            )
        }

        composable(
            "asset_selector?directory={directory}",
            arguments = listOf(navArgument("directory") { type = NavType.StringType })
        ) { backStackEntry ->
            val arguments = backStackEntry.arguments!!
            val directory = arguments.getString("directory")!!
            AssetSelectorScreen(
                directory = directory,
                assetDirectories = viewModel.directoryGroup,
                navigateUp = { navController.navigateUp() },
                onClick = { name ->
                    navController.navigateUp()
                    viewModel.directory = name
                },
            )
        }

        composable(
            "asset_preview?index={index}&requestType={requestType}",
            arguments = listOf(
                navArgument("index") { type = NavType.IntType },
                navArgument("requestType") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val arguments = backStackEntry.arguments!!
            val index = arguments.getInt("index")
            val requestType = arguments.getString("requestType")
            val assets = viewModel.getAssets(RequestType.valueOf(requestType!!))
            AssetPreviewScreen(
                index = index,
                assets = assets,
                selectedList = viewModel.selectedList,
                navigateUp = { navController.navigateUp() },
            )
        }
    }
}