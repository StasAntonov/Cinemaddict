package com.example.cinemaddict.binding

import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("app:backgroundResColor")
fun View.setBackgroundResColor(@ColorRes color: Int) {
    try {
        this.setBackgroundColor(ContextCompat.getColor(context, color))
    } catch (_: Exception) {
    }
}