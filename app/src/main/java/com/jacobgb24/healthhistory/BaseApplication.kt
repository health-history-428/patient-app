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
        private lateinit var context: Context
        lateinit var sharedPreferences: SharedPreferences
            private set
        lateinit var api: ApiInterface
            private set

        fun refreshApi() {
            api = ApiBuilder.getNewApi()
        }

        fun getString(id: Int): String {
            return context.getString(id)
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        sharedPreferences = applicationContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        api = ApiBuilder.getNewApi()
    }


}