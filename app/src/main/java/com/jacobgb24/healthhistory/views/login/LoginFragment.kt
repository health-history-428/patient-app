package com.jacobgb24.healthhistory.views.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.jacobgb24.healthhistory.BaseApplication
import com.jacobgb24.healthhistory.R
import com.jacobgb24.healthhistory.LoginActivity
import com.jacobgb24.healthhistory.MainActivity
import com.jacobgb24.healthhistory.api.Resource
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

        binding.loginButt.setOnClickListener {
            model.tryLogin().observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Resource.Status.SUCCESS -> {
                            binding.loginProgress.visibility = View.GONE
                            BaseApplication.sharedPreferences.edit()
                                .putString("USER_EMAIL", resource.data?.email).apply()
                            startActivity(Intent(activity, MainActivity::class.java))
                        }
                        Resource.Status.LOADING -> {
                            binding.loginProgress.visibility = View.GONE
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