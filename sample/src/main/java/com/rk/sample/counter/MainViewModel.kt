package com.rk.sample.counter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rk.sample.counter.MainContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel(), MainContract.ViewModel {


    init {}


    override val container: Container<State, SideEffect> = container(State()) {}
    override fun dispatch(intent: Intent) = intent {
        viewModelScope.launch {
            when (intent) {
                Intent.BackClick -> {}
                is Intent.ShowReactionsIntent -> intent {
                    reduce {
                        val reaction = ReactionHolder(x = intent.x, y = intent.y,)
                        state.copy(reaction = reaction)
                    }
                    postSideEffect(SideEffect.ReactionPopUp(state.reaction!!))
                }
                is Intent.ShowDialogIntent -> {
                    reduce {
                        state.copy(isDialog = intent.isDialog)
                    }
                }
                is Intent.ShowDatePickerIntent -> {
                    reduce {
                        state.copy(isDatePicker = intent.isDialog)
                    }
                }
                is Intent.ShowTimePickerIntent -> {
                    reduce {
                        state.copy(isTimePicker = intent.isDialog)
                    }
                }
                is Intent.ShowBottomSheet -> {
                    reduce {
                        state.copy(isBottomSheet = intent.isDialog)
                    }
                }
            }
        }
    }


    override fun dismiss(isDialog: Boolean) = intent {
        reduce { state.copy(reaction = null) }
        reduce { state.copy(isDialog = false) }
        reduce { state.copy(isDatePicker = false) }
        reduce { state.copy(isTimePicker = false) }
        reduce { state.copy(isBottomSheet = false) }
    }
}