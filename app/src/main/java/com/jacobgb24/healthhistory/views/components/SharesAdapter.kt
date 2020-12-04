package com.jacobgb24.healthhistory.views.components

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jacobgb24.healthhistory.R
import com.jacobgb24.healthhistory.model.Share
import com.jacobgb24.healthhistory.model.SharedStatus
import kotlinx.android.synthetic.main.list_item_share.view.*

/**
 * RecyclerView adapter used for
 */
class SharesAdapter : RecyclerView.Adapter<SharesAdapter.SharesHolder>() {
    var list = listOf<Share>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    lateinit var approveCallback: (Share) -> Unit
    lateinit var denyCallback: (Share) -> Unit


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SharesHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SharesHolder(inflater.inflate(R.layout.list_item_share, parent, false))
    }

    override fun onBindViewHolder(holder: SharesHolder, position: Int) {
        val share = list[position]
        holder.itemView.text_viewer.text = share.viewer.owner.email

        if (share.status == SharedStatus.DENIED) {
            holder.itemView.button_deny.setBackgroundColor(Color.BLUE)
        }
        else {
            holder.itemView.button_deny.setOnClickListener { denyCallback.invoke(share) }
        }

        if (share.status == SharedStatus.APPROVED) {
            holder.itemView.button_approve.setBackgroundColor(Color.BLUE)
        }
        else {
            holder.itemView.button_approve.setOnClickListener { approveCallback.invoke(share) }
        }

        //TODO: differentiate answered from new
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class SharesHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}