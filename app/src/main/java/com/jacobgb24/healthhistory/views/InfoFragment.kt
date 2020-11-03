package com.jacobgb24.healthhistory.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.jacobgb24.healthhistory.R
import com.jacobgb24.healthhistory.databinding.FragmentPersonalInfoBinding
import com.jacobgb24.healthhistory.quickLog
import com.jacobgb24.healthhistory.viewmodels.InfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.text.DateFormat.MEDIUM

@AndroidEntryPoint
class InfoFragment: Fragment() {
    private val model: InfoViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // bindings setup
        val binding: FragmentPersonalInfoBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_personal_info, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = model

        model.patientInfo.observe(viewLifecycleOwner, Observer {
            quickLog("info updated: $it")
        })

        return binding.root
    }

}