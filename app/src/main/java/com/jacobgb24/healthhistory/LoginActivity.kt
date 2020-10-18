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
import com.jacobgb24.healthhistory.views.login.LoginFragment
import com.jacobgb24.healthhistory.views.login.RegistrationFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dev_dialog.view.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPrefs = getSharedPreferences("PREFS", Context.MODE_PRIVATE)


        if (sharedPrefs.getString("USER_EMAIL", "") != "") {
            setFragment(LoginFragment())
        }
        else {
            setFragment(RegistrationFragment())
        }
    }

    fun setFragment(frag: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, frag)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.login_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.dev_tools -> {
                val dialogView = LayoutInflater.from(this).inflate(R.layout.dev_dialog, null)
                val dialog = AlertDialog.Builder(this).setView(dialogView).setTitle("Dev Tools").show()

                dialogView.dev_ip.setText(sharedPrefs.getString("SERVER_IP", "127.0.0.1"))
                dialogView.dev_port.setText(sharedPrefs.getInt("SERVER_PORT", 8000).toString())

                dialogView.dev_save.setOnClickListener {
                    sharedPrefs.edit().putString("SERVER_IP", dialogView.dev_ip.text.toString())
                    sharedPrefs.edit().putInt("SERVER_PORT", dialogView.dev_port.text.toString().toInt())

                    sharedPrefs.edit().putBoolean("SERVER_MOCK", dialogView.dev_mock.isChecked)
                    dialog.dismiss()
                }

                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

}