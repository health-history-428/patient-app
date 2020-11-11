package com.jacobgb24.healthhistory.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Insurance(
//    var user: User = User(),
    var insurance_company_name: String = "",
    var policy_number: String = "",
    var recipient_last_name: String = "",
    var recipient_first_name: String = "",
    var policy_begin_date: Date = Calendar.getInstance().time,
    var policy_end_date: Date = Calendar.getInstance().time,
    var group_number: String = "",
    var insured_last_name: String = "",
    var insured_first_name: String = "",
    var employer_name: String = "",
    var employer_address: Address = Address()
) : Parcelable