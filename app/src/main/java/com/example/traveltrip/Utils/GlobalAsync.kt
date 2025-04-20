package com.example.traveltrip.Utils

import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object GlobalAsync {
    fun <T> async(
        block: suspend AsyncScope.() -> T,
        onResult: (T) -> Unit,
        onError: (Throwable) -> Unit = {}
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = AsyncScopeImpl().block()
                onResult(result)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    interface AsyncScope {
        suspend fun <T> await(suspendFunc: suspend () -> T): T
    }

    private class AsyncScopeImpl : AsyncScope {
        override suspend fun <T> await(suspendFunc: suspend () -> T): T {
            return suspendFunc()
        }
    }
}
