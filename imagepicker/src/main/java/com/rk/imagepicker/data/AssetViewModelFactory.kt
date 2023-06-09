package com.rk.imagepicker.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController

import com.rk.imagepicker.constant.AssetPickerConfig

class AssetViewModelFactory(
    private val assetPickerRepository: AssetPickerRepository,
    private val assetPickerConfig: AssetPickerConfig,
    private val navController: NavController,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AssetViewModel::class.java)) {
            AssetViewModel(assetPickerRepository, assetPickerConfig, navController) as T
        } else {
            throw IllegalArgumentException("ViewModel is Missing")
        }
    }
}