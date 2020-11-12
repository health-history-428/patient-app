package com.jacobgb24.healthhistory

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.android.material.textfield.TextInputLayout
import java.text.DateFormat
import java.text.DateFormat.MEDIUM
import java.util.*

/**
 * Standard date format used in UI. This is NOT the format used to communicate with the backend
 */
val DATE_FORMATTER: DateFormat = DateFormat.getDateInstance(MEDIUM)

/**
 * Possible Types for the edit dialog
 */
enum class EditDialogType {
    CONTACT,
    HEALTH,
    INSURANCE
}

/**
 * Combines multiple LiveData objects. Will observe each and on change, set own value
 * to whatever `func` returns
 */
fun <S> combineData(vararg data: LiveData<*>, func: () -> S): MediatorLiveData<S> {
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

/*==========================*
 * BINDING ADAPTERS / UTILS *
 *==========================*/

/**
 * Allows DataBinding the errorText of a TextInputLayout
 */
@BindingAdapter("errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
    view.error = errorMessage
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
@InverseMethod("stringToDate")
fun dateToString(date: Date?): String {
    return if (date != null) DATE_FORMATTER.format(date) else ""
}

fun stringToDate(string: String): Date? {
    return DATE_FORMATTER.parse(string)
}