package com.example.cinemaddict.component

import android.content.Context
import android.util.AttributeSet
import com.example.cinemaddict.R
import com.google.android.material.tabs.TabLayout

class CustomTabLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TabLayout(context, attrs, defStyleAttr) {

    init {
        addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: Tab?) {
                setTabColor(tab, true)
            }

            override fun onTabUnselected(tab: Tab?) {
                setTabColor(tab, false)
            }

            override fun onTabReselected(tab: Tab?) {}
        })
    }

    private fun setTabColor(tab: Tab?, isSelected: Boolean) {
            tab?.customView?.findViewById<GradientTextView>(R.id.tv_custom_tab_item)?.let {
                if (isSelected) {
                    it.enableGradient()
                } else {
                    it.disableGradient()
                }
            }
    }
}