package com.jacobgb24.healthhistory.view.login

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.jacobgb24.healthhistory.R
import com.jacobgb24.healthhistory.controller.HealthHistoryApp
import com.jacobgb24.healthhistory.controller.LoginActivity
import com.jacobgb24.healthhistory.databinding.FragmentRegistrationBinding
import com.jacobgb24.healthhistory.viewmodel.RegistrationViewModel
import kotlinx.android.synthetic.main.fragment_registration.view.*

class RegistrationFragment : Fragment() {
    private val model: RegistrationViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentRegistrationBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = model

        binding.switchLoginButt.setOnClickListener {
            (activity as LoginActivity).setFragment(LoginFragment())
        }

        return binding.root
    }

}