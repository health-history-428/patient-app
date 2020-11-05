package com.jacobgb24.healthhistory.views.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.jacobgb24.healthhistory.R
import com.jacobgb24.healthhistory.quickLog
import kotlinx.android.synthetic.main.component_info_item.view.*

/**
 * Header for a group of items in the "Personal Info" tab
 * Contains icon, title, and edit button. Icon/title should be set in xml
 */
class InfoItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.component_info_item, this, true)
        attrs?.let {
            val styledAttributes = context.obtainStyledAttributes(it, R.styleable.InfoItem, 0, 0)
            info_item_key.text = styledAttributes.getString((R.styleable.InfoItem_itemKey))
            info_item_val.text = styledAttributes.getString((R.styleable.InfoItem_itemVal))

            styledAttributes.recycle()

        }
    }

    fun setItemVal(value: String) {
//        quickLog("Set val called $value")
        info_item_val.text = value
    }
}