package com.jacobgb24.healthhistory.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jacobgb24.healthhistory.R
import com.jacobgb24.healthhistory.api.Resource
import com.jacobgb24.healthhistory.databinding.FragmentSharesBinding
import com.jacobgb24.healthhistory.viewmodels.SharesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SharesFragment : Fragment() {
    private val model: SharesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // bindings setup
        val binding: FragmentSharesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_shares, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = model

        model.getShares().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        if (resource.data?.isNotEmpty() == true) {
                            // TODO: Dialog here
                        }
                    }
                    Resource.Status.ERROR -> {
                        Toast.makeText(activity, resource.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }

            }
        })

        return binding.root
    }

}