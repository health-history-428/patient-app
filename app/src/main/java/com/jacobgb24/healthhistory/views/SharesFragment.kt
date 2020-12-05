package com.jacobgb24.healthhistory.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jacobgb24.healthhistory.R
import com.jacobgb24.healthhistory.api.Resource
import com.jacobgb24.healthhistory.databinding.FragmentSharesBinding
import com.jacobgb24.healthhistory.model.Share
import com.jacobgb24.healthhistory.model.SharedStatus
import com.jacobgb24.healthhistory.quickLog
import com.jacobgb24.healthhistory.viewmodels.SharesViewModel
import com.jacobgb24.healthhistory.views.components.SharesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SharesFragment : Fragment() {
    private val model: SharesViewModel by activityViewModels()
    private lateinit var refresh: SwipeRefreshLayout
    private val adapter = SharesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // bindings setup
        val binding: FragmentSharesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_shares, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = model

        refresh = binding.shareRefresh
        refresh.setOnRefreshListener { doRefresh() }

        binding.shareList.adapter = adapter
        adapter.approveCallback = { s: Share -> sendResponse(s, SharedStatus.APPROVED) }
        adapter.denyCallback = { s: Share -> sendResponse(s, SharedStatus.DENIED) }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        doRefresh()
    }

    private fun sendResponse(share: Share, response: SharedStatus) {
        model.respondToShare(share, response).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when(resource.status) {
                    Resource.Status.SUCCESS, Resource.Status.ERROR ->
                        doRefresh()
                    else -> {}
                }
            }
        })
    }

    fun doRefresh() {
        if (isAdded) {
            quickLog("UI refresh")
            model.getShares().observe(viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        Resource.Status.LOADING -> {
                            refresh.isRefreshing = true
                        }
                        Resource.Status.SUCCESS -> {
                            adapter.list = resource.data ?: listOf()
                            refresh.isRefreshing = false
                        }
                        Resource.Status.ERROR -> {
                            Toast.makeText(activity, resource.message, Toast.LENGTH_SHORT).show()
                            refresh.isRefreshing = false
                        }
                    }

                }
            })
        }
    }

}