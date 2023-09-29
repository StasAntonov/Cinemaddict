package com.example.cinemaddict.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation

@BindingAdapter(
    value = ["app:loadImage", "app:cornerImageRadius"],
    requireAll = false
)
fun ImageView.loadImage(url: String, radius: Float? = null) {
    this.load(url) {
        radius?.let { transformations(RoundedCornersTransformation(it, it, it, it)) }
    }
}