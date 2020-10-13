package com.jacobgb24.healthhistory

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.android.material.textfield.TextInputLayout


@BindingAdapter("app:errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
    view.error = errorMessage
}


fun <T,S> combineData(vararg data: LiveData<T>, func: () -> S): MediatorLiveData<S> {
    return MediatorLiveData<S>().apply {
        for (d in data) {
            addSource(d) { value = func.invoke() }
        }
    }
}