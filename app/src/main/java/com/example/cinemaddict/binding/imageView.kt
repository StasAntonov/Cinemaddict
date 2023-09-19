package com.example.cinemaddict.binding

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
//todo improve
@BindingAdapter("app:imageView")
fun setImageView(view: AppCompatImageView, drawable: Int?) {
    drawable?.let {
        view.setImageResource(it)
    }
}