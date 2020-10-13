package com.jacobgb24.healthhistory.server

import kotlin.concurrent.schedule
import java.util.*

object MockServer : ServerInterface {

    override fun setAddr(ip: String, port: Int) {
        TODO("Not yet implemented")
    }

    override fun registerUser(email: String, password: String, handler: (Boolean, String) -> Unit) {
        Timer().schedule(1000) {
            if (password.length > 4) {
                handler.invoke(true, "")
            }
            else {
                handler.invoke(false, "Reasons")
            }
        }
    }

    override fun loginUser(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun preparePassword(password: String) {
        TODO("Not yet implemented")
    }

}