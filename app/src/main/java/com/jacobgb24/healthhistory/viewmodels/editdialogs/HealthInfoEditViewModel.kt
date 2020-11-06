package com.jacobgb24.healthhistory.viewmodels.editdialogs

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jacobgb24.healthhistory.api.ApiInterface
import com.jacobgb24.healthhistory.api.Resource
import com.jacobgb24.healthhistory.getApiError
import com.jacobgb24.healthhistory.model.Contact
import com.jacobgb24.healthhistory.model.PatientInfo
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class HealthInfoEditViewModel @ViewModelInject constructor(
    private var api: ApiInterface,
    private val dispatcher: CoroutineContext
) : ViewModel() {

    val patientInfo: MutableLiveData<PatientInfo> = MutableLiveData()


    fun updatePatientInfo() = liveData(dispatcher) {
        if (patientInfo.value == null) {
            emit(Resource.error(null, "Data was null"))
            return@liveData
        }
        emit(Resource.loading())
        try {
            emit(Resource.success(api.updatePatientInfo(patientInfo.value!!)))
        } catch (e: Exception) {
            emit(Resource.error(null, "Error: ${e.getApiError()}"))
        }
    }
}