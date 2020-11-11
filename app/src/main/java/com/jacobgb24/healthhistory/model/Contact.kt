package com.jacobgb24.healthhistory.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    var user: User = User(),
    var address: Address = Address(),
    var phone: String = "",
    var name: String = ""
) : Parcelable