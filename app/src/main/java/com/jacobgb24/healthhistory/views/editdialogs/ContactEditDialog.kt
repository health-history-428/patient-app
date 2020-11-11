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
import com.jacobgb24.healthhistory.R
import com.jacobgb24.healthhistory.api.Resource
import com.jacobgb24.healthhistory.databinding.DialogEditContactBinding
import com.jacobgb24.healthhistory.viewmodels.editdialogs.ContactEditViewModel
import com.jacobgb24.healthhistory.views.InfoFragment


class ContactEditDialog : DialogFragment() {
    private val model: ContactEditViewModel by activityViewModels()
    private lateinit var progressBar: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: DialogEditContactBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_edit_contact, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = model
        model.contact.value = arguments?.getParcelable("OBJ")

        val toolbar: Toolbar = binding.toolbar
        toolbar.title = "Edit Contact Info"
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)

        progressBar = binding.saveProgress

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.dialog_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                model.updateContactInfo().observe(viewLifecycleOwner, {
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