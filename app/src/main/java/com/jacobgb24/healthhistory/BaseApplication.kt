package com.jacobgb24.healthhistory

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.jacobgb24.healthhistory.api.ApiBuilder
import com.jacobgb24.healthhistory.api.ApiInterface
import dagger.hilt.android.HiltAndroidApp

/**
 * Necessary for Hilt
 */
@HiltAndroidApp
class BaseApplication : Application()