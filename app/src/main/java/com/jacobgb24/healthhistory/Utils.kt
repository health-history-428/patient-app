package com.jacobgb24.healthhistory

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import retrofit2.HttpException
import java.lang.Exception
import java.text.DateFormat
import java.text.DateFormat.MEDIUM
import java.util.*


private val DATE_FORMATTER = DateFormat.getDateInstance(MEDIUM)

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
@InverseMethod("stringToDate")
fun dateToString(date: Date?): String {
    return if (date != null) DATE_FORMATTER.format(date) else ""
}

fun stringToDate(string: String): Date? {
    return DATE_FORMATTER.parse(string)
}

enum class EditDialogType {
    CONTACT,
    HEALTH,
    INSURANCE
}


/**
 * Sets up this edittext to open a date picker on click.
 * Converts date picker value from text value and back
 */
fun TextInputEditText.prepareForDate(context: Context) {
    this.isLongClickable = false
    this.isFocusable = false

    this.setOnClickListener {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val date = Calendar.getInstance()
            date.set(year, month, dayOfMonth)
            quickLog("$year, $month, $dayOfMonth")
            this.setText(dateToString(date.time))

        }
        DATE_FORMATTER.parse(this.text.toString())?.let {
            val cal = Calendar.getInstance()
            cal.time = it

            val datePickerDialog = DatePickerDialog(context, dateSetListener,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))

            datePickerDialog.show()
        }

    }
}
