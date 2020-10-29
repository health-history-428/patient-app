package com.jacobgb24.healthhistory.model

/**
 * User model returned by backend on login/register.
 */
data class User(
    val id: Int = 0,
    val account: Int = 0,
    val email: String = "",
    val user_type: String = "PATIENT",
    val role: String = "OWNER"
)
