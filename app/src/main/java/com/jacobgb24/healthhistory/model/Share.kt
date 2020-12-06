package com.jacobgb24.healthhistory.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Share(
        var id: String = "0",
        @SerializedName("viewer") var viewer_id: String? = null,
        @SerializedName("viewer_obj") var viewer: User? = null,
        var status: SharedStatus = SharedStatus.REQUESTED
) : Parcelable