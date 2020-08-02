package com.an9ar.testauthapp.utils.tooltip

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import com.an9ar.testauthapp.R
import javax.inject.Inject

class TooltipDelegate @Inject constructor(
    private val context: Context
) {

    private val textColor = ContextCompat.getColor(context, R.color.colorBlack)

    fun showTooltip(view: View, text: String, isBottomGravity: Boolean) {
        Tooltip.Builder(view)
            .setText(text)
            .setCancelable(true)
            .setDismissOnClick(true)
            .setGravity(if (isBottomGravity) Gravity.BOTTOM else Gravity.TOP)
            .setPadding(R.dimen.margin_normal)
            .setCornerRadius(R.dimen.corner_radius_normal)
            .setBackgroundColor(ContextCompat.getColor(context, R.color.colorLightGray))
            .setTextColor(textColor)
            .show()
    }
}