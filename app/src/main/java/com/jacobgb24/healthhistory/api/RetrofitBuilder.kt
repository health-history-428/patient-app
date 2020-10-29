package com.jacobgb24.healthhistory.api

import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

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


    internal fun createInstance(ip: String, port: Int): ApiInterface {

        return getRetrofit(ip, port).create(ApiInterface::class.java)
    }


    fun clearCookies() {
        cookieManager.cookieStore.removeAll()
    }


}