package com.jacobgb24.healthhistory.views.login

import android.content.Context
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
import com.jacobgb24.healthhistory.*
import com.jacobgb24.healthhistory.api.Resource
import com.jacobgb24.healthhistory.databinding.FragmentRegistrationBinding
import com.jacobgb24.healthhistory.viewmodels.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment: Fragment() {
    private val model: RegistrationViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentRegistrationBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = model

        val sharedPreferences = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        binding.switchLoginButt.setOnClickListener {
            (activity as LoginActivity).setFragment(LoginFragment())
        }

        binding.registerButt.setOnClickListener {
            model.tryRegister().observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Resource.Status.SUCCESS -> {
                            binding.registerProgress.visibility = View.GONE
                            sharedPreferences?.edit()
                                ?.putString("USER_EMAIL", resource.data?.email)?.apply()
                            startActivity(Intent(activity, MainActivity::class.java))
                        }
                        Resource.Status.LOADING -> {
                            binding.registerProgress.visibility = View.VISIBLE
                        }
                        Resource.Status.ERROR -> {
                            Toast.makeText(activity, resource.message, Toast.LENGTH_SHORT).show()
                            binding.registerProgress.visibility = View.GONE
                        }
                    }
                }
            })
        }

        return binding.root
    }

}