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
 * Contains icon, title, and optional button. Icon/title/button text should be set in xml.
 * If the button text isn't set the button will be hidden. Click action can be set with
 * `setButtonClick()`
 */
class InfoHeader @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.component_info_header, this, true)
        attrs?.let {
            val styledAttributes = context.obtainStyledAttributes(it, R.styleable.InfoHeader, 0, 0)
            info_header_title.text =
                styledAttributes.getString((R.styleable.InfoHeader_headerTitle))
            info_header_icon.setImageDrawable(styledAttributes.getDrawable((R.styleable.InfoHeader_headerIcon)))
            val buttonText = styledAttributes.getString(R.styleable.InfoHeader_headerButtonText)
            if (buttonText == null)
                info_header_button.visibility = GONE
            else
                info_header_button.text = buttonText
            styledAttributes.recycle()

        }
    }

    /**
     * Set what should happen when the button is clicked
     */
    fun setButtonClick(action: (View) -> Unit) {
        info_header_button.setOnClickListener(action)
    }
}