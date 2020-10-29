package com.jacobgb24.healthhistory.api

import com.jacobgb24.healthhistory.model.Insurance
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
        return User(1, 1, "login-mock@api.com")
    }

    override suspend fun registerUser(obj: ApiInterface.RegisterReq): User {
        print("register called")
        if (obj.email.contains("bad"))
            throw createError<User>("Invalid Email")
        return User(2, 2, "register-mock@api.com")    }

    override suspend fun getInsurance(): Insurance {
        TODO("Not yet implemented")
    }

    override suspend fun createInsurance(insurance: Insurance): Insurance {
        TODO("Not yet implemented")
    }

    override suspend fun updateInsurance(insurance: Insurance): Insurance {
        TODO("Not yet implemented")
    }

}