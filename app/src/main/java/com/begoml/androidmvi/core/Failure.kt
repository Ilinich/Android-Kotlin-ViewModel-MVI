package com.begoml.androidmvi.core

sealed class Failure {

    /**
     * Extend this class for domain specific failures.
     */
    abstract class NetworkFailure : Failure() {
        object NetworkConnection : NetworkFailure()
        object EmptyNetworkResponse : NetworkFailure()
        object SocketTimeoutException : NetworkFailure()
        object UnknownHostFailure : NetworkFailure()
        class ServerError(val code: String) : Failure()
    }

    object None: Failure()

    data class UnknownFailure(val error: Exception) : Failure()
}