package com.jacobgb24.healthhistory.model

data class Contact(
    val user: User = User(),
    val address: Address = Address(),
    val phone: String = "",
    val name: String = ""
)