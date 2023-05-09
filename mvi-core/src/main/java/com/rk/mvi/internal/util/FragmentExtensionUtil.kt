package com.rk.mvi.internal.util

import com.rk.mvi.EffectProcessor
import com.rk.mvi.StateEffectProcessor
import com.rk.mvi.StateProcessor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

internal fun <EV : Any, ST : Any> CoroutineScope.consume(
    processor: StateProcessor<EV, ST>,
    render: (ST) -> Unit,
    intents: List<Flow<EV>> = emptyList()
) {
    launch { processor.onState(render) }
    launch { processor.process(intents) }
}

internal fun <EV : Any, EF : Any, ST : Any> CoroutineScope.consume(
    processor: StateEffectProcessor<EV, ST, EF>,
    render: (ST) -> Unit,
    trigger: (EF) -> Unit,
    intents: List<Flow<EV>> = emptyList()
) {
    launch { processor.onState(render) }
    launch { processor.onEffect(trigger) }
    launch { processor.process(intents) }
}

internal fun <EV : Any, EF : Any> CoroutineScope.consume(
    processor: EffectProcessor<EV, EF>,
    trigger: (EF) -> Unit,
    intents: List<Flow<EV>> = emptyList()
) {
    launch { processor.onEffect(trigger) }
    launch { processor.process(intents) }
}

internal suspend fun <EV : Any, ST : Any> StateProcessor<EV, ST>.process(
    viewEvents: List<Flow<EV>> = emptyList()
) = viewEvents.mergeEvents().collect { event -> sendEvent(event) }

internal suspend fun <EV : Any, EF : Any> EffectProcessor<EV, EF>.process(
    viewEvents: List<Flow<EV>> = emptyList()
) = viewEvents.mergeEvents().collect { event -> sendEvent(event) }

internal suspend fun <EV : Any, ST : Any> StateProcessor<EV, ST>.onState(
    render: (ST) -> Unit
): Nothing = state.collect { state -> render(state) }

internal suspend fun <EV : Any, ST : Any, EF : Any> StateEffectProcessor<EV, ST, EF>.onEffect(
    trigger: (EF) -> Unit
) = effect.collect { effect -> trigger(effect) }

internal suspend fun <EV : Any, EF : Any> EffectProcessor<EV, EF>.onEffect(
    trigger: (EF) -> Unit
) = effect.collect { effect -> trigger(effect) }
