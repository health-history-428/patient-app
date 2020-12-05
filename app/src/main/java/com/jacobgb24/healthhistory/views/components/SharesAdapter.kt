package com.jacobgb24.healthhistory.views.components

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jacobgb24.healthhistory.R
import com.jacobgb24.healthhistory.model.Share
import com.jacobgb24.healthhistory.model.SharedStatus
import com.jacobgb24.healthhistory.quickLog
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
        holder.itemView.text_viewer.text = share.viewer?.owner?.email ?: "Unknown"

        quickLog("showing share for ${share.viewer?.owner?.email} w/ status ${share.status}")

        if (share.status == SharedStatus.DENIED) {
            holder.itemView.selection_group.check(R.id.button_deny)
        }
        else if (share.status == SharedStatus.APPROVED) {
            holder.itemView.selection_group.check(R.id.button_approve)
        }


        holder.itemView.selection_group.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                if (checkedId == R.id.button_approve && share.status != SharedStatus.APPROVED) {
                    quickLog("Approving $share")
                    approveCallback.invoke(share)
                }
                else if (checkedId == R.id.button_deny && share.status != SharedStatus.DENIED) {
                    quickLog("Denying $share")
                    denyCallback.invoke(share)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class SharesHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
