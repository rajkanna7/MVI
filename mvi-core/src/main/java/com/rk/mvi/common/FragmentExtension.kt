package com.rk.mvi.common

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.rk.mvi.EffectProcessor
import com.rk.mvi.StateEffectProcessor
import com.rk.mvi.StateProcessor
import com.rk.mvi.internal.util.consume
import kotlinx.coroutines.flow.Flow

@JvmName("OnResumedStateProcessor")
fun <EV : Any, ST : Any> Fragment.onResumed(
    processor: () -> StateProcessor<EV, ST>,
    intents: () -> List<Flow<EV>> = { emptyList() },
    onState: (ST) -> Unit
) = lifecycleScope.launchWhenResumed { consume(processor(), onState, intents()) }

fun <EV : Any, ST : Any, EF : Any> Fragment.onResumed(
    processor: () -> StateEffectProcessor<EV, ST, EF>,
    intents: () -> List<Flow<EV>> = { emptyList() },
    onState: (ST) -> Unit,
    onEffect: (EF) -> Unit
) = lifecycleScope.launchWhenResumed { consume(processor(), onState, onEffect, intents()) }

@JvmName("OnResumedEffectProcessor")
fun <EV : Any, EF : Any> Fragment.onResumed(
    processor: () -> EffectProcessor<EV, EF>,
    intents: () -> List<Flow<EV>> = { emptyList() },
    onEffect: (EF) -> Unit
) = lifecycleScope.launchWhenResumed { consume(processor(), onEffect, intents()) }
