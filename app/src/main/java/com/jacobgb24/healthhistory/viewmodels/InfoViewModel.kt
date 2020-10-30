package com.jacobgb24.healthhistory.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jacobgb24.healthhistory.api.ApiWrapper
import com.jacobgb24.healthhistory.model.Address
import com.jacobgb24.healthhistory.model.Insurance
import com.jacobgb24.healthhistory.model.PatientInfo
import kotlin.coroutines.CoroutineContext

class InfoViewModel @ViewModelInject constructor(
    private var api: ApiWrapper,
    dispatcher: CoroutineContext
) : ViewModel() {

    val patientInfo: MutableLiveData<PatientInfo?> = MutableLiveData(null)
    val insurance: MutableLiveData<Insurance?> = MutableLiveData(null)

    init {
        liveData<Any>(dispatcher) {
            insurance.value = api.getInsurance()
            patientInfo.value = api.getPatientInfo()
        }
    }
}