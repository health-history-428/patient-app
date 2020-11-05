package com.jacobgb24.healthhistory.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class PatientInfo(
//    val patient_id: User = User(),
    val birthday: Date = Calendar.getInstance().time,
    val height: String = "",
    val gender: String = "",
    val allergies: List<String> = emptyList(),
    val medications: List<String> = emptyList(),
    val surgeries: List<String> = emptyList(),
    val existing_conditions: List<String> = emptyList(),
    val family_conditions: List<String> = emptyList()
): Parcelable