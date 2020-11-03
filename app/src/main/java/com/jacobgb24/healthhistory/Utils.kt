package com.jacobgb24.healthhistory

import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import retrofit2.HttpException
import java.lang.Exception
import java.text.DateFormat
import java.text.DateFormat.MEDIUM
import java.util.*


/**
 * Allows DataBinding the errorText of a TextInputLayout
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

/**
 * Attempts to extract the server's error message from the exception, otherwise returns the
 * plain exception message
 */
fun Exception.getApiError(): String {
    if (this is HttpException) {
        val errorJsonString = this.response()?.errorBody()?.string()
        try {
            val parsedString = JsonParser().parse(errorJsonString)
            return parsedString.asJsonObject["error"].asString
        } catch (exception: JsonParseException) { }
    }
    return this.message ?: "Unknown Error"
}

/**
 * DataBinding helper function. Defines how to format a list for the UI
 */
fun <T> listToString(l: List<T>?): String {
    if (l == null || l.isEmpty()) {
        return "None"
    }
    return l.joinToString("; ")
}

/**
 * DataBinding helper function. Defines how to format a date for the UI
 */
fun formatDate(date: Date?): String {
    return if (date != null) DateFormat.getDateInstance(MEDIUM).format(date) else ""
}