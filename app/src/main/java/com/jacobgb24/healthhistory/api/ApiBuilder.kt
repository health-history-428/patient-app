package com.jacobgb24.healthhistory.api

import android.content.Context
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.util.concurrent.TimeUnit

object ApiBuilder {
    private var api: ApiInterface? = null
    private val cookieManager = CookieManager()

    /**
     * OkHttp client that has debugging enabled and is set to store cookies
     */
    private val client: OkHttpClient
        get() {
            val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
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
            .build()
    }


    private fun createInstance(ip: String, port: Int): ApiInterface {
        return getRetrofit(ip, port).create(ApiInterface::class.java)
    }


    /**
     * Clears the cookies in the cookie store. The primary purpose of this is to reset a session
     * if, for example, the user logs out
     */
    fun clearCookies() {
        cookieManager.cookieStore.removeAll()
    }

    /**
     * Resets the API. The primary purpose of this is to reset the URL being used
     * (switching to mock server)
     */
    fun resetApi(context: Context) {
        api = null
        getApi(context)
    }

    /**
     * Get an actual instance of the API. the API object is generally a singleton unless `resetApi`
     * is called
     */
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