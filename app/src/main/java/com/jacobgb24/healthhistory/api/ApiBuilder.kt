package com.jacobgb24.healthhistory.api

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.util.concurrent.TimeUnit

object ApiBuilder {
    private var api: ApiInterface? = null

    private val client: OkHttpClient
        get() {
            val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .cookieJar(JavaNetCookieJar(CookieManager()))
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


    private fun createInstance(context: Context): ApiInterface {
        val sharedPreferences = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        return getRetrofit(
            sharedPreferences.getString("SERVER_IP", "10.0.2.2") ?: "",
            sharedPreferences.getInt("SERVER_PORT", 8000))
            .create(ApiInterface::class.java)
    }

    fun resetUrl(context: Context) {
        api = createInstance(context)
    }

    fun getApi(context: Context): ApiInterface {
        if (api == null) {
            return createInstance(context)
        }
        return api!!
    }
}