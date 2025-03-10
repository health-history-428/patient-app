package com.jacobgb24.healthhistory.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Insurance(
    var insurance_company_name: String = "",
    var policy_number: String = "",
    var recipient_last_name: String = "",
    var recipient_first_name: String = "",
    var policy_begin_date: Date? = null,
    var policy_end_date: Date? = null,
    var group_number: String = "",
    var subscriber_name: String = "",
    var employer_name: String = "",
    @SerializedName("employer_address") var employer_address_id: String? = null,
    @SerializedName("employer_address_info") var employer_address: Address = Address()
) : Parcelable