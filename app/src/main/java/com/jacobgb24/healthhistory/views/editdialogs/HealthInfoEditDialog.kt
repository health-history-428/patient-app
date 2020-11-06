package com.jacobgb24.healthhistory.views.editdialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.CalendarView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.jacobgb24.healthhistory.EditDialogType
import com.jacobgb24.healthhistory.R
import com.jacobgb24.healthhistory.api.Resource
import com.jacobgb24.healthhistory.databinding.DialogEditContactBinding
import com.jacobgb24.healthhistory.databinding.DialogEditHealthBinding
import com.jacobgb24.healthhistory.dateToString
import com.jacobgb24.healthhistory.quickLog
import com.jacobgb24.healthhistory.viewmodels.editdialogs.ContactEditViewModel
import com.jacobgb24.healthhistory.viewmodels.editdialogs.HealthInfoEditViewModel
import com.jacobgb24.healthhistory.views.InfoFragment
import kotlinx.android.synthetic.main.dialog_edit_contact.*
import java.util.*

class HealthInfoEditDialog : DialogFragment() {
    private val model: HealthInfoEditViewModel by activityViewModels()
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: DialogEditHealthBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_edit_health, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = model
        model.patientInfo.value = arguments?.getParcelable("OBJ")

        val toolbar: Toolbar = binding.toolbar
        toolbar.title = "Edit Health Info"
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)

        progressBar = binding.saveProgress

        // todo: refactor to utils method
        binding.editBirthday.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val date = Calendar.getInstance()
                date.set(year, month, dayOfMonth)
                quickLog("$year, $month, $dayOfMonth")
                binding.editBirthday.setText(dateToString(date.time))

            }
            model.patientInfo.value?.birthday?.let {
                val cal = Calendar.getInstance()
                cal.time = it

                val datePickerDialog = DatePickerDialog(requireContext(), dateSetListener,
                    cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))

                datePickerDialog.show()
            }

        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.dialog_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_save -> {
                model.updatePatientInfo().observe(viewLifecycleOwner, {
                    it?.let { resource ->
                        when (resource.status) {
                            Resource.Status.SUCCESS -> {
                                progressBar.visibility = View.GONE
                                (targetFragment as? InfoFragment)?.refreshModel()
                                dismiss()
                            }
                            Resource.Status.LOADING -> {
                                progressBar.visibility = View.VISIBLE
                            }
                            Resource.Status.ERROR -> {
                                Toast.makeText(activity, resource.message, Toast.LENGTH_SHORT).show()
                                progressBar.visibility = View.GONE
                            }
                        }
                    }
                })
            }
            else -> {
                dismiss()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}