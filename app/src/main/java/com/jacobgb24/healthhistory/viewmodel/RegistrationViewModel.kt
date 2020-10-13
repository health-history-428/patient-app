package com.jacobgb24.healthhistory.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.jacobgb24.healthhistory.combineData

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

    val allValid = combineData(email, password, passwordConfirm) {
        email.value?.isNotEmpty() ?: false && emailError.value == null &&
                password.value?.isNotEmpty() ?: false &&
                passwordConfirm.value?.isNotEmpty() ?: false && passwordConfirmError.value == null
    }

}