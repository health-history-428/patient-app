package com.jacobgb24.healthhistory.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.jacobgb24.healthhistory.api.ApiInterface
import com.jacobgb24.healthhistory.api.Resource
import com.jacobgb24.healthhistory.combineData
import com.jacobgb24.healthhistory.getApiError
import com.jacobgb24.healthhistory.model.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class MainViewModel @ViewModelInject constructor(
        private var api: ApiInterface,
        private val dispatcher: CoroutineContext
) : ViewModel() {


    fun getShares() = liveData<Resource<List<Share>>>(dispatcher) {
        while(true) {
            try {
                emit(Resource.success(api.getPendingShares()))
            } catch (e: Exception) {
                emit(Resource.error(null, "Error: ${e.getApiError()}"))
            }
            kotlinx.coroutines.delay(5000)
        }
    }

    fun respondToShare(share: Share, response: SharedStatus) = liveData(dispatcher) {
        emit(Resource.loading())
        try {
            val res = ApiInterface.ShareResponse(share.id)
            when(response) {
                SharedStatus.APPROVED -> {
                    emit(Resource.success(api.approveShare(res)))
                }
                SharedStatus.DENIED -> {
                    emit(Resource.success(api.denyShare(res)))
                }
                else -> {}
            }
        } catch (e: Exception) {
            emit(Resource.error(null, "Error: ${e.getApiError()}"))
        }
    }

}