package di

import android.content.Context
import com.jacobgb24.healthhistory.api.ApiWrapper
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
    @Singleton
    fun api(@ApplicationContext context: Context): ApiWrapper {
        return ApiWrapper(context)
    }

    @Provides
    @Singleton
    fun couroutineDispatcher(): CoroutineContext = Dispatchers.IO
}