package com.jacobgb24.healthhistory.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jacobgb24.healthhistory.LoginActivity
import com.jacobgb24.healthhistory.MainActivity
import com.jacobgb24.healthhistory.R
import com.jacobgb24.healthhistory.api.Resource
import com.jacobgb24.healthhistory.databinding.FragmentLoginBinding
import com.jacobgb24.healthhistory.doOnEnter
import com.jacobgb24.healthhistory.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val model: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // bindings setup
        val binding: FragmentLoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = model

        val sharedPreferences = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)


        binding.switchRegisterButt.setOnClickListener {
            (activity as LoginActivity).setFragment(RegistrationFragment())
        }

        binding.loginPassword.requestFocus()

        // click login button if user presses enter in field and fields are valid
        binding.loginEmail.doOnEnter { if (model.allValid.value == true) binding.loginButt.callOnClick() }
        binding.loginPassword.doOnEnter { if (model.allValid.value == true) binding.loginButt.callOnClick() }

        // login button handler
        binding.loginButt.setOnClickListener {
            model.tryLogin().observe(viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        Resource.Status.SUCCESS -> {
                            binding.loginProgress.visibility = View.GONE
                            sharedPreferences?.edit()
                                ?.putString("USER_EMAIL", resource.data?.email)?.apply()
                            startActivity(Intent(activity, MainActivity::class.java))
                        }
                        Resource.Status.LOADING -> {
                            binding.loginProgress.visibility = View.VISIBLE
                        }
                        Resource.Status.ERROR -> {
                            Toast.makeText(activity, resource.message, Toast.LENGTH_SHORT).show()
                            binding.loginProgress.visibility = View.GONE
                        }
                    }
                }
            })
        }

        return binding.root
    }

}