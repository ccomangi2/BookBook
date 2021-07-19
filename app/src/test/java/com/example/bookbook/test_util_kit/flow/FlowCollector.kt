package com.meuus90.booksearcher.test_util_kit.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

suspend fun <T> Flow<T>.test(
    scope: CoroutineScope,
    block: suspend FlowTestCollector<T>.() -> Unit
): Job {
    return scope.launch(coroutineContext) {
        FlowTestCollectorImpl(this@test).block()
    }
}