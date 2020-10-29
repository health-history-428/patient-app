package com.jacobgb24.healthhistory.api

import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import retrofit2.HttpException

/**
 * Simple wrapper class for a network error. If it's an HttpException, we'll get the error from json
 */
class ApiError constructor(error: Throwable) {
    var errorMsg =  error.message ?: "Unknown Error"
    var code = 0

    init {
        if (error is HttpException) {
            code = error.code()
            val errorJsonString = error.response()?.errorBody()?.string()
            try {
                val parsedString = JsonParser().parse(errorJsonString)
                this.errorMsg = parsedString.asJsonObject["error"].asString
            } catch (exception: JsonParseException) { }
        }
    }
}