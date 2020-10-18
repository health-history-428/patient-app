package com.jacobgb24.healthhistory.views.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.jacobgb24.healthhistory.R
import com.jacobgb24.healthhistory.LoginActivity
import com.jacobgb24.healthhistory.api.Status
import com.jacobgb24.healthhistory.databinding.FragmentRegistrationBinding
import com.jacobgb24.healthhistory.quickLog
import com.jacobgb24.healthhistory.viewmodels.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private val model: RegistrationViewModel by activityViewModels()

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

        binding.registerButt.setOnClickListener {
            model.tryRegister().observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                            quickLog(resource.data.toString())
                            binding.registerProgress.visibility = View.GONE
                        }
                        Status.LOADING -> {
                            binding.registerProgress.visibility = View.VISIBLE
                        }
                        Status.ERROR -> {
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