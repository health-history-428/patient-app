package com.jacobgb24.healthhistory.api

import com.jacobgb24.healthhistory.BaseApplication
import com.jacobgb24.healthhistory.quickLog
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiBuilder {

    private fun buildURL(): String {
        return "http://%s:%d/".format(
            BaseApplication.sharedPreferences.getString("SERVER_IP", "127.0.0.1"),
            BaseApplication.sharedPreferences.getInt("SERVER_PORT", 8000))
    }

    private fun getDebugClient(): OkHttpClient {
        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(buildURL())
            .addConverterFactory(GsonConverterFactory.create())
            .client(getDebugClient())
            .build() //Doesn't require the adapter
    }

    fun getNewApi(): ApiInterface {
        quickLog(buildURL())
        return getRetrofit().create(ApiInterface::class.java)
    }
}