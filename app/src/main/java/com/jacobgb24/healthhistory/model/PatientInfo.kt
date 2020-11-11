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
    var allergies: MutableList<String> = mutableListOf(),
    var medications: MutableList<String> = mutableListOf(),
    var surgeries: MutableList<String> = mutableListOf(),
    var existing_conditions: MutableList<String> = mutableListOf(),
    var family_conditions: MutableList<String> = mutableListOf()
) : Parcelable