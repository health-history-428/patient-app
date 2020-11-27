package com.jacobgb24.healthhistory.viewmodels.editdialogs

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jacobgb24.healthhistory.api.ApiInterface
import com.jacobgb24.healthhistory.api.Resource
import com.jacobgb24.healthhistory.getApiError
import com.jacobgb24.healthhistory.model.Insurance
import kotlin.coroutines.CoroutineContext

class InsuranceEditViewModel @ViewModelInject constructor(
    private var api: ApiInterface,
    private val dispatcher: CoroutineContext
) : ViewModel() {

    val insurance: MutableLiveData<Insurance> = MutableLiveData()


    fun updateInsurance() = liveData(dispatcher) {
        if (insurance.value == null) {
            emit(Resource.error(null, "Data was null"))
            return@liveData
        }
        emit(Resource.loading())
        try {
            emit(Resource.success(api.updateInsurance(insurance.value!!)))
        } catch (e: Exception) {
            emit(Resource.error(null, "Error: ${e.getApiError()}"))
        }
    }
}