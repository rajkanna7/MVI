package com.rk.sample.counter

import androidx.compose.runtime.*
import org.orbitmvi.orbit.ContainerHost


interface MainContract {


    interface ViewModel : ContainerHost<State, SideEffect> {
        fun dispatch(intent: Intent)
        fun dismiss(isDialog: Boolean = false)
    }

    @Immutable
    data class State(
        val reaction: ReactionHolder?,
        var isDialog: Boolean,
        var isDatePicker: Boolean,
        var isTimePicker: Boolean,
        var isBottomSheet: Boolean,

    )

    data class ReactionHolder(
        val x: Int,
        val y: Int,
    )

    sealed interface Intent {
        object BackClick : Intent
        data class ShowReactionsIntent(val x: Int, val y: Int) : Intent
        data class ShowDialogIntent(val isDialog: Boolean) : Intent
        data class ShowDatePickerIntent(val isDialog: Boolean) : Intent
        data class ShowTimePickerIntent(val isDialog: Boolean) : Intent
        data class ShowBottomSheet(val isDialog: Boolean) : Intent
    }

    sealed interface SideEffect {
        data class ReactionPopUp(val reaction: ReactionHolder) : SideEffect
        data class DialogInfo(val isDialog: Boolean) : SideEffect
    }


}

fun State(): MainContract.State = MainContract.State(
    reaction = null,
    isDialog = false,
    isDatePicker = false,
    isTimePicker = false,
    isBottomSheet = false,
)