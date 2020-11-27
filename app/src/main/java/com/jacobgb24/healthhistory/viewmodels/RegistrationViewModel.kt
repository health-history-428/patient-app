package com.jacobgb24.healthhistory.viewmodels

import android.util.Patterns
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jacobgb24.healthhistory.api.ApiInterface
import com.jacobgb24.healthhistory.api.Resource
import com.jacobgb24.healthhistory.combineData
import com.jacobgb24.healthhistory.getApiError
import kotlin.coroutines.CoroutineContext

class RegistrationViewModel @ViewModelInject constructor(
    private var api: ApiInterface,
    private val dispatcher: CoroutineContext
) : ViewModel() {
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val passwordConfirm = MutableLiveData("")


    val emailError = Transformations.map(email) { e ->
        if (e.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(e).matches()) null else "Invalid email"
    }
    val passwordConfirmError = combineData(password, passwordConfirm) {
        if (passwordConfirm.value?.isEmpty() == true || password.value == passwordConfirm.value)
            null
        else
            "Passwords do not match"
    }

    val allValid = combineData(email, password, passwordConfirm, passwordConfirmError, emailError) {
        email.value?.isNotEmpty() ?: false && emailError.value == null &&
                password.value?.isNotEmpty() ?: false &&
                passwordConfirm.value?.isNotEmpty() ?: false && passwordConfirmError.value == null
    }

    fun tryRegister() = liveData(dispatcher) {
        emit(Resource.loading())
        try {
            emit(
                Resource.success(
                    api.registerUser(
                        ApiInterface.RegisterReq(
                            email.value ?: "",
                            password.value ?: "",
                            passwordConfirm.value ?: ""
                        )
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(null, "Error: ${e.getApiError()}"))
        }
    }

}