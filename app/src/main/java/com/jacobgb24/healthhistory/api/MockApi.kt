package com.jacobgb24.healthhistory.api

import com.jacobgb24.healthhistory.model.User
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

/**
 * A mock API useful for testing. This can be set in the dev menu on the login page
 */
class MockApi: ApiInterface {

    /**
     * Helpful function for creating an exception like what the real server produces
     */
    private inline fun <reified T> createError(err: String, code: Int = 400): HttpException {
        throw HttpException(Response.error<T>(
            code,
            "{\"error\":[\"${err}\"]}".toResponseBody("application/json".toMediaTypeOrNull())
        ))
    }


    override suspend fun loginUser(obj: ApiInterface.LoginReq): User {
        if (obj.email.contains("bad"))
            throw createError<User>("Invalid Email")
        return User("a@v.c")
    }

    override suspend fun registerUser(obj: ApiInterface.RegisterReq): User {
        print("register called")
        if (obj.email.contains("bad"))
            throw createError<User>("Invalid Email")
        return User("a@v.c")
    }

}