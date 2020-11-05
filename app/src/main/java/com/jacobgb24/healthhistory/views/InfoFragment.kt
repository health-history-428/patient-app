package com.jacobgb24.healthhistory.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.jacobgb24.healthhistory.EditDialogType
import com.jacobgb24.healthhistory.R
import com.jacobgb24.healthhistory.databinding.FragmentPersonalInfoBinding
import com.jacobgb24.healthhistory.model.Contact
import com.jacobgb24.healthhistory.model.Insurance
import com.jacobgb24.healthhistory.model.PatientInfo
import com.jacobgb24.healthhistory.quickLog
import com.jacobgb24.healthhistory.viewmodels.InfoViewModel
import com.jacobgb24.healthhistory.views.editdialogs.ContactEditDialog
import com.jacobgb24.healthhistory.views.editdialogs.HealthInfoEditDialog
import com.jacobgb24.healthhistory.views.editdialogs.InsuranceEditDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment: Fragment() {
    private val model: InfoViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // bindings setup
        val binding: FragmentPersonalInfoBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_personal_info, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = model

        binding.infoContactHeader.setEditClick { showEditDialog(EditDialogType.CONTACT) }
        binding.infoHealthHeader.setEditClick { showEditDialog(EditDialogType.HEALTH) }
        binding.infoInsuranceHeader.setEditClick { showEditDialog(EditDialogType.INSURANCE) }

        return binding.root
    }


    private fun showEditDialog(type: EditDialogType) {
        val bundle = Bundle()
        val fragment: DialogFragment
        fragment = when(type) {
            EditDialogType.CONTACT -> {
                bundle.putParcelable("OBJ", model.contact.value?.data ?: Contact())
                ContactEditDialog()

            }
            EditDialogType.HEALTH -> {
                bundle.putParcelable("OBJ", model.patientInfo.value?.data ?: PatientInfo())
                HealthInfoEditDialog()
            }
            EditDialogType.INSURANCE -> {
                bundle.putParcelable("OBJ", model.insurance.value?.data ?: Insurance())
                InsuranceEditDialog()
            }
        }
        fragment.arguments = bundle
        fragment.setTargetFragment(this, 1)


        parentFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .add(android.R.id.content, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun refreshModel() {
        quickLog("refresh called")
        model.refresh()
    }

}