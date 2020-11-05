package com.jacobgb24.healthhistory.views.editdialogs

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import com.jacobgb24.healthhistory.EditDialogType
import com.jacobgb24.healthhistory.R
import kotlinx.android.synthetic.main.dialog_edit_contact.*

class InsuranceEditDialog : DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //todo change layout
        val rootView: View = inflater.inflate(R.layout.dialog_edit_contact, container, false)
        val toolbar: Toolbar = rootView.findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.title = "Edit Health Info"
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)

        //todo connect to viewmodel

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.dialog_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_save -> {
                //todo api call
                dismiss()
            }
            else -> {
                dismiss()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}