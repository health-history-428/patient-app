package com.jacobgb24.healthhistory.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * User model returned by backend on login/register.
 */
@Parcelize
data class User(
    val id: Int = 0,
    val account: Int = 0,
    val email: String = "",
    val user_type: String = "PATIENT",
    val role: String = "OWNER"
): Parcelable
