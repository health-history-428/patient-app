package com.jacobgb24.healthhistory.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.jacobgb24.healthhistory.api.ApiInterface
import com.jacobgb24.healthhistory.api.Resource
import com.jacobgb24.healthhistory.combineData
import com.jacobgb24.healthhistory.getApiError
import com.jacobgb24.healthhistory.model.Contact
import com.jacobgb24.healthhistory.model.Insurance
import com.jacobgb24.healthhistory.model.PatientInfo
import retrofit2.HttpException
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class InfoViewModel @ViewModelInject constructor(
    private var api: ApiInterface,
    private val dispatcher: CoroutineContext
) : ViewModel() {
    // we set this little guy up so that whenever we set its value the livedata is updated
    private val refreshTrigger = MutableLiveData(Unit)

    val patientInfo: LiveData<Resource<PatientInfo>> = getData(api::getPatientInfo, PatientInfo())
    val insurance: LiveData<Resource<Insurance>> = getData(api::getInsurance, Insurance())
    val contact: LiveData<Resource<Contact>> = getData(api::getContact, Contact())

    val isLoading = combineData(patientInfo, insurance, contact) {
        patientInfo.value?.status == Resource.Status.LOADING ||
                insurance.value?.status == Resource.Status.LOADING ||
                contact.value?.status == Resource.Status.LOADING
    }

    init {
        refresh()
    }

    fun refresh() {
        refreshTrigger.value = Unit
    }

    /**
     * Gets the data using `apiCall` and if the backend returns an error uses `defaultInstance`.
     */
    private fun <T> getData(apiCall: suspend () -> T, defaultInstance: T): LiveData<Resource<T>> =
        refreshTrigger.switchMap {
            liveData(dispatcher) {

                emit(Resource.loading())
                try {
                    emit(Resource.success(apiCall.invoke()))
                } catch (e: Exception) {
                    if (e is HttpException && e.code() == 404) {
                        // user doesn't have existing data, so we'll return an empty one successfully
                        emit(Resource.success(defaultInstance))
                    }
                    emit(Resource.error(defaultInstance, e.getApiError()))
                }
            }
        }

}