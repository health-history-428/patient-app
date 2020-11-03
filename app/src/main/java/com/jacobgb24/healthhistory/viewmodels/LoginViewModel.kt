package com.jacobgb24.healthhistory.viewmodels

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
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
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class LoginViewModel @ViewModelInject constructor(
    @ApplicationContext context: Context,
    private var api: ApiInterface,
    private val dispatcher: CoroutineContext
) : ViewModel() {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("PREFS", MODE_PRIVATE)

    val email = MutableLiveData(sharedPreferences.getString("USER_EMAIL", "") ?: "")
    val password = MutableLiveData("")

    val emailError = Transformations.map(email) { e ->
        if (e.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(e).matches()) null else "Invalid email"
    }
    val passwordError = MutableLiveData<String?>(null)


    val allValid = combineData(email, password, emailError, passwordError) {
        email.value?.isNotEmpty() ?: false && emailError.value == null &&
                password.value?.isNotEmpty() ?: false
    }

    fun tryLogin() = liveData(dispatcher) {
        emit(Resource.loading())
        try {
            emit(
                Resource.success(
                    api.loginUser(
                        ApiInterface.LoginReq(
                            email.value ?: "",
                            password.value ?: ""
                        )
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(null, "Error: ${e.getApiError()}"))
        }
    }

}