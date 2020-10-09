package com.jacobgb24.healthhistory.controller

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jacobgb24.healthhistory.view.login.LoginFragment
import com.jacobgb24.healthhistory.view.login.RegistrationFragment

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getPreferences(Context.MODE_PRIVATE)

        if (sharedPref.getString("USER_EMAIL", "") != "") {
            setFragment(LoginFragment())
        }
        else {
            setFragment(RegistrationFragment())
        }
    }

    fun setFragment(frag: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, frag)
            .commit()
    }

}