package com.jacobgb24.healthhistory

import com.jacobgb24.healthhistory.api.ApiInterface
import com.jacobgb24.healthhistory.model.User
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

class MockApi: ApiInterface {

    private inline fun <reified T> createError(err: String): HttpException {
        throw HttpException(Response.error<T>(
            400,
            "{\"error\":[\"${err}\"]}".toResponseBody("application/json".toMediaTypeOrNull())
        ))
    }


    override suspend fun loginUser(obj: ApiInterface.LoginReq): User {
        if (obj.email.contains("bad"))
            throw createError<User>("Invalid Email")
        return User(1, "a@v.c", "1")
    }

    override suspend fun registerUser(obj: ApiInterface.RegisterReq): User {
        print("register called")
        if (obj.email.contains("bad"))
            throw createError<User>("Invalid Email")
        return User(1, "a@v.c", "1")
    }

}