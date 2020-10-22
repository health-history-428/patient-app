package com.jacobgb24.healthhistory.api

import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.jacobgb24.healthhistory.quickLog
import retrofit2.HttpException

class ApiError constructor(error: Throwable) {
    var error = "Unknown Error"
    var code = 0

    init {
        if (error is HttpException) {
            code = error.code()
            val errorJsonString = error.response()?.errorBody()?.string()
            try {
                val parsedString = JsonParser().parse(errorJsonString)
                this.error = parsedString.asJsonObject["error"].asString
            } catch (exception: JsonParseException) { }
        }
    }
}