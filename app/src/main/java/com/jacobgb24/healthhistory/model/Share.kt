package com.jacobgb24.healthhistory.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Share(
        var id: String = "0",
        var owner: User = User(),
        var requester: Account = Account(),
        var status: SharedStatus = SharedStatus.REQUESTED
) : Parcelable