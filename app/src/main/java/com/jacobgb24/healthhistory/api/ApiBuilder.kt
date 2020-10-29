package com.jacobgb24.healthhistory.api

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieHandler
import java.net.CookieManager
import java.util.concurrent.TimeUnit

object ApiBuilder {
    private var api: ApiInterface? = null

    private val cookieManager = CookieManager()

    private val client: OkHttpClient
        get() {
            val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .cookieJar(JavaNetCookieJar(cookieManager))
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
        }

    private fun getRetrofit(ip: String, port: Int): Retrofit {
        val url = "http://%s:%d/".format(ip, port)
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build() //Doesn't require the adapter
    }


    private fun createInstance(ip: String, port: Int): ApiInterface {

        return getRetrofit(ip, port).create(ApiInterface::class.java)
    }

    fun resetUrl(context: Context) {
        api = null
        getApi(context)
    }

    fun clearCookies() {
        cookieManager.cookieStore.removeAll()
    }

    fun getApi(context: Context): ApiInterface {
        val sharedPreferences = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        val useMock = sharedPreferences.getBoolean("SERVER_MOCK", false)
        val ipStr = sharedPreferences.getString("SERVER_IP", "10.0.2.2") ?: ""
        val portInt = sharedPreferences.getInt("SERVER_PORT", 8000)
        if (api == null) {
            return if (useMock) MockApi() else createInstance(ipStr, portInt)
        }
        return api!!
    }
}