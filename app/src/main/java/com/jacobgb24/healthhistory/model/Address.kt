package com.jacobgb24.healthhistory.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    var address_1: String = "",
    var address_2: String = "",
    var city: String = "",
    var state: String = "",
    var zip_code: String = "",
) : Parcelable {
    override fun toString(): String {
        if (address_1.isEmpty() && city.isEmpty() && state.isEmpty() && zip_code.isEmpty())
            return ""
        return "$address_1\n$address_2${if (address_2.isNotEmpty()) "\n" else ""}$city, $state, $zip_code"
    }
}