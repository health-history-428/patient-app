package com.jacobgb24.healthhistory.views.editdialogs

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.jacobgb24.healthhistory.*
import com.jacobgb24.healthhistory.api.Resource
import com.jacobgb24.healthhistory.databinding.DialogEditHealthBinding
import com.jacobgb24.healthhistory.viewmodels.editdialogs.HealthInfoEditViewModel
import com.jacobgb24.healthhistory.views.InfoFragment
import com.jacobgb24.healthhistory.views.components.EditAdapter
import com.jacobgb24.healthhistory.views.components.MaterialSpinnerAdapter


class HealthInfoEditDialog : DialogFragment() {
    private val model: HealthInfoEditViewModel by activityViewModels()
    private lateinit var progressBar: ProgressBar
    private val allergyAdapter = EditAdapter()
    private val medicationAdapter = EditAdapter()
    private val surgeryAdapter = EditAdapter()
    private val existingCondAdapter = EditAdapter()
    private val familyCondAdapter = EditAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        binding.editBirthday.prepareForDate(requireContext())

        binding.editGender.setAdapter(
            MaterialSpinnerAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,
                arrayOf("Male", "Female", "Other")
            ))

        binding.allergiesList.adapter = allergyAdapter
        allergyAdapter.list = model.patientInfo.value?.allergies ?: mutableListOf()
        binding.allergiesHeader.setEditClick { allergyAdapter.addItem() }

        binding.medicationsList.adapter = medicationAdapter
        medicationAdapter.list = model.patientInfo.value?.medications ?: mutableListOf()
        binding.medicationsHeader.setEditClick { medicationAdapter.addItem() }

        binding.surgeriesList.adapter = surgeryAdapter
        surgeryAdapter.list = model.patientInfo.value?.surgeries ?: mutableListOf()
        binding.surgeriesHeader.setEditClick { surgeryAdapter.addItem() }

        binding.existingCondList.adapter = existingCondAdapter
        existingCondAdapter.list = model.patientInfo.value?.existing_conditions ?: mutableListOf()
        binding.existingCondHeader.setEditClick { existingCondAdapter.addItem() }

        binding.familyCondList.adapter = familyCondAdapter
        familyCondAdapter.list = model.patientInfo.value?.family_conditions ?: mutableListOf()
        binding.familyCondHeader.setEditClick { familyCondAdapter.addItem() }


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.dialog_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_save -> {
                // update our lists now with what the adapters currently hold
                model.patientInfo.value?.allergies = allergyAdapter.getTrimmedList() as MutableList<String>
                model.patientInfo.value?.medications = medicationAdapter.getTrimmedList() as MutableList<String>
                model.patientInfo.value?.surgeries = surgeryAdapter.getTrimmedList() as MutableList<String>
                model.patientInfo.value?.existing_conditions = existingCondAdapter.getTrimmedList() as MutableList<String>
                model.patientInfo.value?.family_conditions = familyCondAdapter.getTrimmedList() as MutableList<String>

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
                                Toast.makeText(activity, resource.message, Toast.LENGTH_SHORT)
                                    .show()
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