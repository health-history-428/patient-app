package com.jacobgb24.healthhistory.model

import java.util.*

data class Insurance(
    val user: User = User(),
    val insurance_company_name: String = "",
    val policy_number: String = "",
    val recipient_last_name: String = "",
    val recipient_first_name: String = "",
    val policy_begin_date: Date = Calendar.getInstance().time,
    val policy_end_date: Date = Calendar.getInstance().time,
    val group_number: String = "",
    val insured_last_name: String = "",
    val insured_first_name: String = "",
    val employer_name: String = "",
    val employer_address: Address = Address()
)