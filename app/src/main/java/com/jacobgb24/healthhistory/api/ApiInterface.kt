package com.jacobgb24.healthhistory.api

import com.jacobgb24.healthhistory.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    data class Login(val email: String, val password: String)
    @POST("login")
    suspend fun loginUser(@Body obj: Login): User


    data class Register(val email: String, val password: String, val confirm_password: String)
    @POST("register")
    suspend fun registerUser(@Body obj: Register): User
}