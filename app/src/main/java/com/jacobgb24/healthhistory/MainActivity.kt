package com.jacobgb24.healthhistory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
    }


    private fun setFragment(frag: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, frag)
            .commit()
    }
}