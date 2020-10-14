package com.jacobgb24.healthhistory

import android.app.Application
import android.content.SharedPreferences
import com.jacobgb24.healthhistory.api.MockServer
import com.jacobgb24.healthhistory.api.Server
import com.jacobgb24.healthhistory.api.ServerInterface
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    companion object {
        lateinit var server: ServerInterface

        public fun setServer(isMock: Boolean) {
            server = if (isMock) MockServer else Server
        }
    }



}