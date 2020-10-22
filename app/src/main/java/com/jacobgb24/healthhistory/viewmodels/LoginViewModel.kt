package com.jacobgb24.healthhistory.viewmodels

import android.content.Context
import android.util.Patterns
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jacobgb24.healthhistory.BaseApplication
import com.jacobgb24.healthhistory.api.ApiError
import com.jacobgb24.healthhistory.api.ApiInterface
import com.jacobgb24.healthhistory.api.Resource
import com.jacobgb24.healthhistory.combineData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class LoginViewModel @ViewModelInject constructor(@ApplicationContext application: Context): ViewModel() {
    private val sharedPreferences = application.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
    // TODO: test email gets set properly
    val email = MutableLiveData(sharedPreferences.getString("USER_EMAIL", "") ?: "")
    val password = MutableLiveData("")


    val emailError = Transformations.map(email) { e ->
        if (e.isEmpty()  || Patterns.EMAIL_ADDRESS.matcher(e).matches()) null else "Invalid email"
    }
    val passwordError = MutableLiveData<String?>(null)


    val allValid = combineData(email, password, emailError, passwordError) {
        email.value?.isNotEmpty() ?: false && emailError.value == null &&
                password.value?.isNotEmpty() ?: false
    }

    fun tryLogin() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            emit(
                Resource.success(BaseApplication.api.loginUser(
                    ApiInterface.LoginReq(
                    email.value ?: "",
                    password.value ?: ""
                    )
            )))
        } catch (exception: Exception) {
            emit(Resource.error(null, "Error: ${ApiError(exception).error}"))
        }
    }

}