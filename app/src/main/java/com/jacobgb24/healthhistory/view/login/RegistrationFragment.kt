package com.jacobgb24.healthhistory.view.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacobgb24.healthhistory.R
import com.jacobgb24.healthhistory.controller.LoginActivity
import kotlinx.android.synthetic.main.fragment_registration.view.*

class RegistrationFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_registration, container, false)

        view.switch_login_butt.setOnClickListener {
            (activity as LoginActivity).setFragment(LoginFragment())
        }

        return view
    }

}