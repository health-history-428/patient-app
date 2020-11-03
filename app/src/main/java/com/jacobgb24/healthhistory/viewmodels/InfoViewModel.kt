package com.jacobgb24.healthhistory.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jacobgb24.healthhistory.api.ApiWrapper
import com.jacobgb24.healthhistory.model.Address
import com.jacobgb24.healthhistory.model.Contact
import com.jacobgb24.healthhistory.model.Insurance
import com.jacobgb24.healthhistory.model.PatientInfo
import com.jacobgb24.healthhistory.quickLog
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class InfoViewModel @ViewModelInject constructor(
    private var api: ApiWrapper,
    dispatcher: CoroutineContext
) : ViewModel() {

    val patientInfo: LiveData<PatientInfo?> = liveData(dispatcher) {
        emit(api.getPatientInfo())
    }

    val insurance: LiveData<Insurance> = liveData(dispatcher) {
        api.getInsurance()
    }

    val contact: LiveData<Contact> = liveData(dispatcher) {
        api.getContactInfo()
    }
}