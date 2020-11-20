package com.jacobgb24.healthhistory.di

import android.content.Context
import com.jacobgb24.healthhistory.api.ApiBuilder
import com.jacobgb24.healthhistory.api.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

/**
 * Defines how Hilt should inject different values
 */
@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    fun api(@ApplicationContext context: Context): ApiInterface {
        return ApiBuilder.getApi(context)
    }

    @Provides
    @Singleton
    fun coroutineDispatcher(): CoroutineContext = Dispatchers.IO
}