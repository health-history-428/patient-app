package com.jacobgb24.healthhistory

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.jacobgb24.healthhistory.api.ApiBuilder
import com.jacobgb24.healthhistory.api.ApiInterface
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    companion object {
        lateinit var sharedPreferences: SharedPreferences
        lateinit var api: ApiInterface
    }

    override fun onCreate() {
        super.onCreate()
        sharedPreferences = applicationContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        api = ApiBuilder.getNewApi()
    }

    fun refreshApi() {
        api = ApiBuilder.getNewApi()
    }

}