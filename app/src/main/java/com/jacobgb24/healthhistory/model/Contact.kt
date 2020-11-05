package com.jacobgb24.healthhistory.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    val user: User = User(),
    val address: Address = Address(),
    val phone: String = "",
    val name: String = ""
): Parcelable