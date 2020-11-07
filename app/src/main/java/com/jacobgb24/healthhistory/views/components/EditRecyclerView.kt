package com.jacobgb24.healthhistory.views.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jacobgb24.healthhistory.R
import com.jacobgb24.healthhistory.quickLog
import kotlinx.android.synthetic.main.list_item_edit.view.*

class EditRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    init {
        adapter = EditAdapter()
        layoutManager = LinearLayoutManager(context)
    }


    class EditAdapter : RecyclerView.Adapter<EditAdapter.EditHolder>() {
        var strings: MutableList<String> = mutableListOf()
            set(value) {
                this.strings.clear()
                this.strings.addAll(value)
                notifyDataSetChanged()
            }
        private var callback: (List<String>) -> Unit = {}

        fun setCallback(callback: (List<String>) -> Unit) {
            this.callback = callback
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditHolder {
            val inflater = LayoutInflater.from(parent.context)
            return EditHolder(inflater.inflate(R.layout.list_item_edit, parent, false))
        }

        override fun onBindViewHolder(holder: EditHolder, position: Int) {
            quickLog("bind view holder for pos: $position which is ${strings[position]}")
            holder.bind(strings[position]) { s ->
                if (s == null)
                    strings.removeAt(position)
                else
                    strings[position] = s
                callback.invoke(strings)
            }
        }

        override fun getItemCount(): Int {
            return strings.filter { it.trim().isNotEmpty() }.size
        }

        class EditHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

            fun bind(string: String, changeNotifier: (String?) -> Unit) {
                itemView.item_edittext.editText?.setText(string)
                itemView.item_edittext.editText?.addTextChangedListener { changeNotifier.invoke(it.toString()) }

                itemView.item_remove_butt.setOnClickListener { changeNotifier.invoke(null) }
            }

        }

    }
}

@BindingAdapter("strings")
fun setStrings(view: EditRecyclerView, newStrings: MutableList<String>?) {
    quickLog("set strings to ${newStrings?.joinToString("; ")}")
    (view.adapter as EditRecyclerView.EditAdapter).strings = newStrings ?: mutableListOf()
}

@InverseBindingAdapter(attribute = "strings", event = "app:stringsAttrChanged")
fun getStrings(view: EditRecyclerView): MutableList<String>? {
    quickLog("got strings ${(view.adapter as EditRecyclerView.EditAdapter).strings.joinToString("; ")}")
    return (view.adapter as EditRecyclerView.EditAdapter).strings
}

@BindingAdapter("app:stringsAttrChanged")
fun setListeners(view: EditRecyclerView, attrChange: InverseBindingListener) {
    (view.adapter as EditRecyclerView.EditAdapter).setCallback {
        quickLog("User change fired")
        attrChange.onChange()
    }
}

