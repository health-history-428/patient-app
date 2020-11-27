package com.jacobgb24.healthhistory

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Necessary for Hilt
 */
@HiltAndroidApp
class BaseApplication : Application()