package com.rk.mvi.test

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class MainCoroutineScopeExtension(
    val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : BeforeEachCallback, AfterEachCallback, TestCoroutineScope by TestCoroutineScope(dispatcher) {

    init {
        Dispatchers.setMain(dispatcher)
    }

    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(dispatcher)
    }

    override fun afterEach(context: ExtensionContext?) {
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}
