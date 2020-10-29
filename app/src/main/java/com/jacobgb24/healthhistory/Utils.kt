package com.jacobgb24.healthhistory

import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.android.material.textfield.TextInputLayout


/**
 * Allows DataBinding the errorText of an TextInputLayout
 */
@BindingAdapter("errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
    view.error = errorMessage
}


/**
 * Combines multiple LiveData objects. Will observe each and on change, set own value
 * to whatever `func` returns
 */
fun <T,S> combineData(vararg data: LiveData<T>, func: () -> S): MediatorLiveData<S> {
    return MediatorLiveData<S>().apply {
        for (d in data) {
            addSource(d) { value = func.invoke() }
        }
    }
}

/**
 * Logging method which automatically sets tag to whatever class calls the method
 */
fun quickLog(msg: String) {
    Log.e(Throwable().stackTrace[1].className.split(".").last(), msg)
}

/**
 * Run `action` when the user presses enter on this field.
 * Also sets ime options which is a prereq for listening to events
 */
fun TextInputLayout.doOnEnter(action: () -> Unit) {
    this.editText?.imeOptions = IME_ACTION_SEARCH
    this.editText?.setOnKeyListener { _, keyCode, event ->
        if (event.action == ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
            action.invoke()
            true
        }
        else {
            false
        }
    }
}