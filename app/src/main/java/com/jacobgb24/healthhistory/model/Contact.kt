package com.jacobgb24.healthhistory.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    @SerializedName("address") var address_id: String? = null,
    @SerializedName("address_info") var address: Address? = Address(),
    var phone: String = "",
    var name: String = ""
) : Parcelable