package com.jacobgb24.healthhistory.api

import com.jacobgb24.healthhistory.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * This interface defines all API calls to the backend.
 * We create a concrete Retrofit class in {@link ApiBuilder}
 */
interface ApiInterface {

    /*
     * Login / Registration
     */
    data class LoginReq(val email: String, val password: String)

    @POST("login")
    suspend fun loginUser(@Body obj: LoginReq): User

    data class RegisterReq(val email: String, val password: String, val confirm_password: String)

    @POST("register")
    suspend fun registerUser(@Body obj: RegisterReq): User

    /*
     * Data
     */
    @GET("insurance")
    suspend fun getInsurance(): Insurance

    @POST("insurance")
    suspend fun updateInsurance(@Body insurance: Insurance): Insurance

    @GET("patient_information")
    suspend fun getPatientInfo(): PatientInfo

    @POST("patient_information")
    suspend fun updatePatientInfo(@Body info: PatientInfo): PatientInfo

    @GET("contact")
    suspend fun getContact(): Contact

    @POST("contact")
    suspend fun updateContact(@Body contact: Contact): Contact

    @GET("address/{id}")
    suspend fun getAddress(@Path("id") id: String): Address


    /*
     * Shares
     */
    @GET("share/owner")
    suspend fun getAllShares(): Map<String, Share>

    data class ShareResponse(val share_id: String)

    @POST("share/approve")
    suspend fun approveShare(@Body shareResponse: ShareResponse): Share

    @POST("share/deny")
    suspend fun denyShare(@Body shareResponse: ShareResponse): Share

    @GET("share/request/{acc_id}")
    suspend fun getViewer(@Path("acc_id") accountId: String): User
}