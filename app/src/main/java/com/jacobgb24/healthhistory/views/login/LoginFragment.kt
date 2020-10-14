package com.jacobgb24.healthhistory.views.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.jacobgb24.healthhistory.R
import com.jacobgb24.healthhistory.LoginActivity
import com.jacobgb24.healthhistory.databinding.FragmentLoginBinding
import com.jacobgb24.healthhistory.databinding.FragmentRegistrationBinding
import com.jacobgb24.healthhistory.viewmodels.LoginViewModel
import com.jacobgb24.healthhistory.viewmodels.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.view.*

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val model: LoginViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentLoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = model

        binding.switchRegisterButt.setOnClickListener {
            (activity as LoginActivity).setFragment(RegistrationFragment())
        }

        return binding.root
    }

}