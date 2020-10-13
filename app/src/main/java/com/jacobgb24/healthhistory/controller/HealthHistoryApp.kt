package com.jacobgb24.healthhistory.controller

import android.app.Application
import com.jacobgb24.healthhistory.server.MockServer
import com.jacobgb24.healthhistory.server.Server
import com.jacobgb24.healthhistory.server.ServerInterface

class HealthHistoryApp : Application() {
    companion object {
        lateinit var server: ServerInterface

        public fun setServer(isMock: Boolean) {
            server = if (isMock) MockServer else Server
        }
    }



}