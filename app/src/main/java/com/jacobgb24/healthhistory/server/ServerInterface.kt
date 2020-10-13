package com.jacobgb24.healthhistory.server

interface ServerInterface {
    fun setAddr(ip: String, port: Int)

    fun  registerUser(email: String, password: String, handler: (Boolean, String) -> Unit)
    fun loginUser(email: String, password: String)

    fun preparePassword(password: String ) //TODO: salt + hash here?

}