package com.jacobgb24.healthhistory.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    val address_1: String = "",
    val address_2: String = "",
    val city: String = "",
    val state: String = "",
    val zip_code: String ="",
): Parcelable {
    override fun toString(): String {
        return "$address_1\n$address_2${if (address_2.isNotEmpty()) "\n" else ""}$city, $state, $zip_code"
    }
}