package com.jacobgb24.healthhistory.api

/**
 * Wrapper class for API responses. Used to emit multiple values from LiveData
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message)

        fun <T> loading(): Resource<T> =
            Resource(status = Status.LOADING, data = null, message = null)
    }
}