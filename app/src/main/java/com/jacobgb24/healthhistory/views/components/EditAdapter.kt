package com.jacobgb24.healthhistory.views.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.jacobgb24.healthhistory.R
import kotlinx.android.synthetic.main.list_item_edit.view.*

/**
 * RecyclerView adapter used for lists of EditTexts. Can add an empty item via `addItem()`.
 * Individual views have ability to remove themselves
 */
class EditAdapter : RecyclerView.Adapter<EditAdapter.EditHolder>() {
    var list = mutableListOf<String>()
        set(value) {
            list.clear()
            list.addAll(value)
            // if list is empty, we'll add a blank entry so there's an edittext visible
            if (value.size == 0)
                list.add("")
            notifyDataSetChanged()
        }


    /**
     * add an empty item to the list
     */
    fun addItem() {
        list.add("")
        notifyItemInserted(list.size)
    }

    /**
     * Get the list of current strings, but filtered to remove any empty strings
     */
    fun getTrimmedList(): List<String> {
        return list.filter { it.trim().isNotEmpty() }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EditHolder(inflater.inflate(R.layout.list_item_edit, parent, false))
    }

    override fun onBindViewHolder(holder: EditHolder, position: Int) {
        holder.itemView.item_edittext.editText?.setText(list[position])
        holder.itemView.item_edittext.editText?.addTextChangedListener {
            list[position] = it.toString()
        }
        holder.itemView.item_remove_butt.setOnClickListener {
            list.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, list.size)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class EditHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}



