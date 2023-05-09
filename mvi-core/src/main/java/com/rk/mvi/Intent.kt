package com.rk.mvi

interface Intent<T> {
    fun reduce(oldState: T): T
}
