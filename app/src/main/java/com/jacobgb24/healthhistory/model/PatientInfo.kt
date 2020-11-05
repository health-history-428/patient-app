package com.jacobgb24.healthhistory.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class PatientInfo(
//    var patient_id: User = User(),
    var birthday: Date = Calendar.getInstance().time,
    var height: String = "",
    var gender: String = "",
    var allergies: List<String> = emptyList(),
    var medications: List<String> = emptyList(),
    var surgeries: List<String> = emptyList(),
    var existing_conditions: List<String> = emptyList(),
    var family_conditions: List<String> = emptyList()
): Parcelable