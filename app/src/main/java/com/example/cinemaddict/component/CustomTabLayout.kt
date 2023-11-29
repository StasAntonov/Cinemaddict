package com.example.cinemaddict.component

import android.content.Context
import android.util.AttributeSet
import com.example.cinemaddict.R
import com.google.android.material.tabs.TabLayout

class CustomTabLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TabLayout(context, attrs, defStyleAttr) {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: Tab?) {
                tab?.customView?.findViewById<GradientTextView>(R.id.tv_custom_tab_item)
                    ?.setTextColorGradient()
            }

            override fun onTabUnselected(tab: Tab?) {
                tab?.customView?.findViewById<GradientTextView>(R.id.tv_custom_tab_item)?.setColor()
            }

            override fun onTabReselected(tab: Tab?) {}
        })
    }

}