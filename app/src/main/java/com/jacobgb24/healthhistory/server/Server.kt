package com.jacobgb24.healthhistory.server

object Server: ServerInterface {
    override fun setAddr(ip: String, port: Int) {
        TODO("Not yet implemented")
    }

    override fun  registerUser(email: String, password: String, handler: (Boolean, String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun loginUser(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun preparePassword(password: String) {
        TODO("Not yet implemented")
    }

}