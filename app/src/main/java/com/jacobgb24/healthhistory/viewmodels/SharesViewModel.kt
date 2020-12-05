package com.jacobgb24.healthhistory.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.jacobgb24.healthhistory.api.ApiInterface
import com.jacobgb24.healthhistory.api.Resource
import com.jacobgb24.healthhistory.getApiError
import com.jacobgb24.healthhistory.model.*
import com.jacobgb24.healthhistory.quickLog
import kotlin.coroutines.CoroutineContext

class SharesViewModel @ViewModelInject constructor(
        private var api: ApiInterface,
        private val dispatcher: CoroutineContext
) : ViewModel() {


    fun getShares() = liveData<Resource<List<Share>>>(dispatcher) {
        emit(Resource.loading())
        try {
            emit(Resource.success(api.getAllShares()))
        } catch (e: Exception) {
            emit(Resource.error(null, "Error: ${e.getApiError()}"))
        }
    }

    fun respondToShare(share: Share, response: SharedStatus) = liveData(dispatcher) {
        quickLog("model respond to share ${share.viewer.owner.email} $response")
        emit(Resource.loading())
        try {
            val req = ApiInterface.ShareResponse(share.id)
            when(response) {
                SharedStatus.APPROVED -> {
                    emit(Resource.success(api.approveShare(req)))
                }
                SharedStatus.DENIED -> {
                    emit(Resource.success(api.denyShare(req)))
                }
                else -> {}
            }
        } catch (e: Exception) {
            emit(Resource.error(null, "Error: ${e.getApiError()}"))
        }
    }

}