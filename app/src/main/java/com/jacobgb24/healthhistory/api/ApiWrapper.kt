package com.jacobgb24.healthhistory.api

import android.content.Context
import com.jacobgb24.healthhistory.model.Insurance
import com.jacobgb24.healthhistory.model.PatientInfo
import com.jacobgb24.healthhistory.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import javax.inject.Inject

/**
 * Main class components should call.
 * This wraps some of the more complex API calls for added convenience
 */
class ApiWrapper @Inject constructor(@ApplicationContext val context: Context) {
    private var api: ApiInterface = createApiInstance()

    private fun createApiInstance(): ApiInterface {
        val sharedPreferences = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        val useMock = sharedPreferences.getBoolean("SERVER_MOCK", false)
        val ipStr = sharedPreferences.getString("SERVER_IP", "10.0.2.2") ?: ""
        val portInt = sharedPreferences.getInt("SERVER_PORT", 8000)
        return if (useMock) MockApi() else RetrofitBuilder.createInstance(ipStr, portInt)
    }

    /** reset the api to use a new url/mock */
    fun resetApi() {
        api = createApiInstance()
    }


    suspend fun loginUser(obj: ApiInterface.LoginReq): User {
        return api.loginUser(obj)
    }

    suspend fun registerUser(obj: ApiInterface.RegisterReq): User {
        return api.registerUser(obj)
    }

    /**
     * Get the insurance if we can. If it doesn't exist create an empty one and try to return that
     */
    suspend fun getInsurance(): Insurance? {
        try {
            return api.getInsurance()
        } catch (e: HttpException) {
            if (e.code() == 404) {
                return api.createInsurance(Insurance())
            }
        }
        return null
    }

    /**
     * Get the insurance if we can. If it doesn't exist create an empty one and try to return that
     */
    suspend fun getPatientInfo(): PatientInfo? {
        try {
            return api.getPatientInfo()
        } catch (e: HttpException) {
            if (e.code() == 404) {
                return api.createPatientInfo(PatientInfo())
            }
        }
        return null
    }

}