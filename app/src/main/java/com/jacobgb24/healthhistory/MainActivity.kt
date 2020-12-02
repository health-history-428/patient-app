package com.jacobgb24.healthhistory

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.jacobgb24.healthhistory.api.Resource
import com.jacobgb24.healthhistory.viewmodels.MainViewModel
import com.jacobgb24.healthhistory.views.InfoFragment
import com.jacobgb24.healthhistory.views.SharesFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

/**
 * MainActivity reached after the login/register pages.
 * Shows either the InfoFragment or VisitsFragment depending on user selection of bottom nav bar
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val model: MainViewModel by viewModels()

    private var currentFrag: Fragment? = null
    private val infoFragment = InfoFragment()
    private val sharesFragment = SharesFragment()

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
                    setFragment(sharesFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }

        model.notifyPending().observe(this, {
            it?.let {
                if (it.status == Resource.Status.SUCCESS && currentFrag != sharesFragment) {
                    Snackbar.make(main_container, "${it.data} pending share request(s)", Snackbar.LENGTH_LONG)
                            .setAction("RESPOND") { setFragment(sharesFragment) }
                            .show()
                }
            }
        })
    }


    private fun setFragment(frag: Fragment) {
        currentFrag = frag
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, frag)
            .commit()
    }
}