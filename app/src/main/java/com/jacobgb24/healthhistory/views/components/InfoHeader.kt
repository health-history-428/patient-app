package com.jacobgb24.healthhistory.views.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.jacobgb24.healthhistory.R
import kotlinx.android.synthetic.main.component_info_header.view.*

/**
 * Header for a group of items in the "Personal Info" tab
 * Contains icon, title, and edit button. Icon/title should be set in xml
 */
class InfoHeader @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.component_info_header, this, true)
        attrs?.let {
            val styledAttributes = context.obtainStyledAttributes(it, R.styleable.InfoHeader, 0, 0)
            info_header_title.text = styledAttributes.getString((R.styleable.InfoHeader_headerTitle))
            info_header_icon.setImageDrawable(styledAttributes.getDrawable((R.styleable.InfoHeader_headerIcon)))
            info_header_button.text = styledAttributes.getString(R.styleable.InfoHeader_headerButtonText)
            styledAttributes.recycle()

        }
    }

    fun setEditClick(action: (View) -> Unit) {
        info_header_button.setOnClickListener(action)
    }
}