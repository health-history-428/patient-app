package com.jacobgb24.healthhistory.view.login

import android.widget.Button
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout


fun enableButtonWhenComplete(butt: Button, vararg fields: TextInputLayout) {
    for (f in fields) {
        f.editText?.doAfterTextChanged {
            butt.isEnabled = fields.all { f -> f.editText?.text?.isNotEmpty() ?: false  && f.error == null }
        }
    }
}

fun validateEmail(field: TextInputLayout) {
    field.editText?.doAfterTextChanged {
        if (it != null && it.isNotEmpty() && !Regex(".+@.+\\..+").matches(it.toString())) {
            field.error = "Not a valid Email Address"
        }
        else {
            field.error = null
        }
    }
}


