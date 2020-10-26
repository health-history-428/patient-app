package com.jacobgb24.healthhistory.api

import com.jacobgb24.healthhistory.model.User
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * This interface defines all API calls to the backend.
 * We create a concrete Retrofit class in {@link ApiBuilder}
 */
interface ApiInterface {

    data class LoginReq(val email: String, val password: String)
    @POST("login")
    suspend fun loginUser(@Body obj: LoginReq): User


    data class RegisterReq(val email: String, val password: String, val confirm_password: String)
    @POST("register")
    suspend fun registerUser(@Body obj: RegisterReq): User
}