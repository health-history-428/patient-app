package com.jacobgb24.healthhistory.model

data class PatientInfo(
    val patient_id: User,
    val age: Int,
    val height: String,
    val gender: String,
    val address: Address,
    val allergies: List<String>,
    val medications: List<String>,
    val surgeries: List<String>,
    val existing_conditions: List<String>,
    val family_conditions: List<String>
)