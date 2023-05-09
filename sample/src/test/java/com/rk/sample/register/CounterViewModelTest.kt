package com.rk.sample.register

import com.rk.mvi.test.BaseCoroutineTest
import com.rk.mvi.test.processorTest
import com.rk.sample.counter.state.CounterEffect
import com.rk.sample.counter.state.CounterEvent
import com.rk.sample.counter.state.CounterState
import org.junit.jupiter.api.Test

internal class CounterViewModelTest : BaseCoroutineTest() {

    private val viewModel: CounterViewModel = CounterViewModel()

    @Test
    fun `when increase event emitted then state changed`() = processorTest(
        given = viewModel::processor,
        whenEvent = CounterEvent.Increase,
        thenStates = {
            assertValues(
                CounterState(value = 0),
                CounterState(value = 1)
            )
        }
    )

    @Test
    fun `when decrease event emitted then state changed`() = processorTest(
        given = viewModel::processor,
        whenEvent = CounterEvent.Decrease,
        thenStates = {
            assertValues(
                CounterState(value = 0),
                CounterState(value = -1)
            )
        }
    )

    @Test
    fun `when navigate to second screen event emitted then effect emitted`() = processorTest(
        given = viewModel::processor,
        whenEvent = CounterEvent.NavigateToSecondScreen,
        thenStates = {
            assertValues(
                CounterState(value = 0)
            )
        },
        thenEffects = {
            assertValues(
                CounterEffect.NavigateToSecondScreen
            )
        }
    )
}
