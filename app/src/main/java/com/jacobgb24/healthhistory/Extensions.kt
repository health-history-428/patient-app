package com.jacobgb24.healthhistory

import android.app.DatePickerDialog
import android.content.Context
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import retrofit2.HttpException
import java.lang.NullPointerException
import java.text.ParseException
import java.util.*

/**
 * A little helper to let the livedata know it has changed
 */
fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}

/**
 * Run `action` when the user presses enter on this field.
 * Also sets ime options which is a prereq for listening to events
 */
fun TextInputLayout.doOnEnter(action: () -> Unit) {
    this.editText?.imeOptions = EditorInfo.IME_ACTION_GO
    this.editText?.setOnKeyListener { _, keyCode, event ->
        if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
            action.invoke()
            true
        } else {
            false
        }
    }
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
            this.setText(dateToString(date.time))

        }
        val cal = Calendar.getInstance()
        try {
            DATE_FORMATTER.parse(this.text.toString())?.let {
                cal.time = it
            }
        } catch (ignored: ParseException) { }

        DatePickerDialog(
            context, dateSetListener,
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        ).show()


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
            return try {
                parsedString.asJsonObject["error"].asString
            } catch (exception: NullPointerException) {
                parsedString.toString()
            }
        } catch (ignored: JsonParseException) {}
    }
    return this.message ?: "Unknown Error"
}