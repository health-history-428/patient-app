package com.jacobgb24.healthhistory.model

data class PatientInfo(
    val patient_id: User = User(),
    val age: Int = -1,
    val height: String = "",
    val gender: String = "",
    val address: Address = Address(),
    val allergies: List<String> = emptyList(),
    val medications: List<String> = emptyList(),
    val surgeries: List<String> = emptyList(),
    val existing_conditions: List<String> = emptyList(),
    val family_conditions: List<String> = emptyList()
)