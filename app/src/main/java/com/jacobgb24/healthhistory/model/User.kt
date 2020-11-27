package com.jacobgb24.healthhistory.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * User model returned by backend on login/register.
 */
@Parcelize
data class User(
    var id: Int = 0,
    var account: Int = 0,
    var email: String = "",
    var user_type: String = "PATIENT",
    var role: String = "OWNER"
) : Parcelable
