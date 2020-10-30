package com.jacobgb24.healthhistory.api

import com.jacobgb24.healthhistory.model.Insurance
import com.jacobgb24.healthhistory.model.PatientInfo
import com.jacobgb24.healthhistory.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

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


    @GET("insurance")
    suspend fun getInsurance(): Insurance
    @POST("insurance")
    suspend fun createInsurance(insurance: Insurance): Insurance
    @PUT("insurance")
    suspend fun updateInsurance(insurance: Insurance): Insurance

    @GET("patient_information")
    suspend fun getPatientInfo(): PatientInfo
    @POST("patient_information")
    suspend fun createPatientInfo(info: PatientInfo): PatientInfo
    @PUT("patient_information")
    suspend fun updatePatientInfo(info: PatientInfo): PatientInfo
}