package com.jacobgb24.healthhistory.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.jacobgb24.healthhistory.api.ApiWrapper
import kotlin.coroutines.CoroutineContext

class InfoViewModel @ViewModelInject constructor(
    private var api: ApiWrapper,
    private val dispatcher: CoroutineContext
) : ViewModel() {

//    val patientInfo: MutableLiveData<PatientInfo> = null

}