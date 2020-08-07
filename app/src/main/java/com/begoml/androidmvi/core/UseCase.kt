package com.begoml.androidmvi.core

import com.begoml.androidmvi.tools.ResultWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<Result, in Params> where Result : Any {

    abstract suspend fun run(params: Params): ResultWrapper<Failure, Result>

    open operator fun invoke(
        asyncScope: CoroutineScope,
        launchScope: CoroutineScope,
        params: Params,
        result: (ResultWrapper<Failure, Result>) -> Unit = {}
    ) {
        val backgroundJob = asyncScope.async {
            run(params)
        }

        try {
            launchScope.launch {
                val resultWrapper = backgroundJob.await()
                result(resultWrapper)
            }
        } catch (error: Exception) {
            launchScope.launch { result(ResultWrapper.Error(Failure.UnknownFailure(error))) }
        }
    }

    object None
}