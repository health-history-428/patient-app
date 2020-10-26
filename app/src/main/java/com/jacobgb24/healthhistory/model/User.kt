package com.jacobgb24.healthhistory.model

/**
 * User model returned by backend on login/register. We only care about the email
 */
data class User(
    val email: String
)
