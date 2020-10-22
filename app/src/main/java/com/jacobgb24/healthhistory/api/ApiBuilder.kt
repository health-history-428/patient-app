package com.jacobgb24.healthhistory.api

import com.jacobgb24.healthhistory.BaseApplication
import com.jacobgb24.healthhistory.quickLog
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.util.concurrent.TimeUnit

object ApiBuilder {

    private fun buildURL(): String {
        return "http://%s:%d/".format(
            BaseApplication.sharedPreferences.getString("SERVER_IP", "10.0.2.2"),
            BaseApplication.sharedPreferences.getInt("SERVER_PORT", 8000))
    }

    private val client: OkHttpClient
        get() {
            val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .cookieJar(JavaNetCookieJar(CookieManager()))
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
        }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(buildURL())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build() //Doesn't require the adapter
    }

    fun getNewApi(): ApiInterface {
        quickLog(buildURL())
        return getRetrofit().create(ApiInterface::class.java)
    }
}