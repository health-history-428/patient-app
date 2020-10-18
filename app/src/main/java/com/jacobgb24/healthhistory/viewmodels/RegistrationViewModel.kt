package com.jacobgb24.healthhistory.viewmodels

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jacobgb24.healthhistory.BaseApplication
import com.jacobgb24.healthhistory.api.ApiInterface
import com.jacobgb24.healthhistory.api.Resource
import com.jacobgb24.healthhistory.combineData
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import java.lang.Exception

class RegistrationViewModel: ViewModel() {
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val passwordConfirm = MutableLiveData("")


    val emailError = Transformations.map(email) { e ->
        if (e.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(e).matches()) null else "Invalid email"
    }
    val passwordError = MutableLiveData<String?>(null)
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

    fun tryRegister() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(BaseApplication.api.registerUser(
                ApiInterface.Register(
                    email.value ?: "",
                    password.value ?: "",
                    passwordConfirm.value ?: ""
                )
            )))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error"))
        }
    }

}