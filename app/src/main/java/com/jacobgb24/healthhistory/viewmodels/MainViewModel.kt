package com.jacobgb24.healthhistory.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.jacobgb24.healthhistory.api.ApiInterface
import com.jacobgb24.healthhistory.api.Resource
import com.jacobgb24.healthhistory.combineData
import com.jacobgb24.healthhistory.getApiError
import com.jacobgb24.healthhistory.model.*
import com.jacobgb24.healthhistory.quickLog
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

/**
 * ViewModel for main activity. This activity has a very minimal view (since it primarily holds
 * fragments). We use this view model to check for new shares and show a snackbar.
 */
class MainViewModel @ViewModelInject constructor(
        private var api: ApiInterface,
        private val dispatcher: CoroutineContext
) : ViewModel() {

    private var lastSeenShares = emptySet<String>()

    fun notifyPending() = liveData(dispatcher) {
        while(true) {
            kotlinx.coroutines.delay(5000)
            try {
                val currentPending = api.getAllShares().values.filter { it.status == SharedStatus.REQUESTED }
                val ids = HashSet(currentPending.map { it.id })
                if (ids != lastSeenShares && ids.isNotEmpty()) {
                    emit(Resource.success(ids.size))
                }
                lastSeenShares = ids
            } catch (e: Exception) {
                emit(Resource.error(null, "Error: ${e.getApiError()}"))
            }
            quickLog("fired shares")
        }
    }

}