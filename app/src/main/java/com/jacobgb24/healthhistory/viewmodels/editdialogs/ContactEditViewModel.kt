package com.jacobgb24.healthhistory.viewmodels.editdialogs

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jacobgb24.healthhistory.api.ApiInterface
import com.jacobgb24.healthhistory.api.Resource
import com.jacobgb24.healthhistory.getApiError
import com.jacobgb24.healthhistory.model.Contact
import kotlin.coroutines.CoroutineContext

class ContactEditViewModel @ViewModelInject constructor(
    private var api: ApiInterface,
    private val dispatcher: CoroutineContext
) : ViewModel() {

    val contact: MutableLiveData<Contact> = MutableLiveData()


    fun updateContactInfo() = liveData(dispatcher) {
        if (contact.value == null) {
            emit(Resource.error(null, "Data was null"))
            return@liveData
        }
        emit(Resource.loading())
        try {
            emit(Resource.success(api.updateContact(contact.value!!)))
        } catch (e: Exception) {
            emit(Resource.error(null, "Error: ${e.getApiError()}"))
        }
    }
}