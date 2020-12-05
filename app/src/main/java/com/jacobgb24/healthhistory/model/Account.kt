package com.jacobgb24.healthhistory.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Account(
        @SerializedName("owner") var owner_id: String? = null,
        @SerializedName("owner_obj") var owner: User? = null
) : Parcelable
