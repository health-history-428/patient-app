package com.jacobgb24.healthhistory

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jacobgb24.healthhistory.api.ApiBuilder
import com.jacobgb24.healthhistory.views.LoginFragment
import com.jacobgb24.healthhistory.views.RegistrationFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.dialog_dev.view.*

/**
 * Starting activity when the app is launched. Presents either a login or registration fragment.
 * If a user has previously logged in we show that fragment otherwise we show registration.
 *
 */
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)

        // if we have a saved email start on the login screen
        sharedPrefs = getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        if (sharedPrefs.getString("USER_EMAIL", "") != "") {
            setFragment(LoginFragment())
        } else {
            setFragment(RegistrationFragment())
        }

    }

    override fun onStart() {
        super.onStart()
        // clear cookies so session is reset
        ApiBuilder.clearCookies()
    }

    fun setFragment(frag: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, frag)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.login_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.dev_tools -> {
                val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_dev, null)
                AlertDialog.Builder(this).setView(dialogView).setTitle("Dev Tools").show()

                dialogView.dev_ip.setText(sharedPrefs.getString("SERVER_IP", "10.0.2.2"))
                dialogView.dev_port.setText(sharedPrefs.getInt("SERVER_PORT", 8000).toString())
                dialogView.dev_mock.isChecked = sharedPrefs.getBoolean("SERVER_MOCK", false)

                dialogView.dev_save.setOnClickListener {
                    sharedPrefs.edit()
                        .putString("SERVER_IP", dialogView.dev_ip.text.toString())
                        .putInt("SERVER_PORT", dialogView.dev_port.text.toString().toInt())
                        .putBoolean("SERVER_MOCK", dialogView.dev_mock.isChecked)
                        .apply()

                    ApiBuilder.resetApi(applicationContext)
                    finish()
                    startActivity(intent)
                }

                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

}