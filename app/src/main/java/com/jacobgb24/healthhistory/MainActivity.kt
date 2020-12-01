package com.jacobgb24.healthhistory

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.jacobgb24.healthhistory.api.Resource
import com.jacobgb24.healthhistory.viewmodels.InfoViewModel
import com.jacobgb24.healthhistory.viewmodels.MainViewModel
import com.jacobgb24.healthhistory.views.InfoFragment
import com.jacobgb24.healthhistory.views.VisitsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

/**
 * MainActivity reached after the login/register pages.
 * Shows either the InfoFragment or VisitsFragment depending on user selection of bottom nav bar
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val model: MainViewModel by viewModels()

    private val infoFragment = InfoFragment()
    private val visitsFragment = VisitsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setFragment(infoFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.main_info_item -> {
                    setFragment(infoFragment)
                    true
                }
                R.id.main_visits_item -> {
                    setFragment(visitsFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }

        model.getShares().observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        if (resource.data?.isNotEmpty() == true) {
                            // TODO: Dialog here
                        }
                    }
                    Resource.Status.ERROR -> {
                        Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }

            }
        })
    }


    private fun setFragment(frag: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, frag)
            .commit()
    }
}