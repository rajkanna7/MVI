package com.rk.mvi

interface Effects<T : Any> {
    fun send(effect: T)
}
