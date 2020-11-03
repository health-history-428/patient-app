package com.jacobgb24.healthhistory.model

data class Address(
    val address_1: String = "",
    val address_2: String = "",
    val city: String = "",
    val state: String = "",
    val zip_code: String ="",
) {
    override fun toString(): String {
        return "$address_1\n$address_2${if (address_2.isNotEmpty()) "\n" else ""}$city, $state, $zip_code"
    }
}