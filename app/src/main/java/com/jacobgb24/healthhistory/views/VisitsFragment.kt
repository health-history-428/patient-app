package com.jacobgb24.healthhistory.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.jacobgb24.healthhistory.R
import com.jacobgb24.healthhistory.databinding.FragmentPastVisitsBinding
import com.jacobgb24.healthhistory.databinding.FragmentPersonalInfoBinding
import com.jacobgb24.healthhistory.viewmodels.InfoViewModel
import com.jacobgb24.healthhistory.viewmodels.VisitsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VisitsFragment: Fragment() {
    private val model: VisitsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // bindings setup
        val binding: FragmentPastVisitsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_past_visits, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = model



        return binding.root
    }

}