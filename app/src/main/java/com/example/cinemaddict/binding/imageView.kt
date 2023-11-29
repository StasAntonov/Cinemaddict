package com.example.cinemaddict.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.cinemaddict.R

@BindingAdapter(
    value = ["app:loadImage", "app:cornerImageRadius"],
    requireAll = false
)
fun ImageView.loadImage(url: String?, radius: Float) {
    url?.let {
        this.load("https://image.tmdb.org/t/p/w500/$it") {
            transformations(RoundedCornersTransformation(radius))
        }
    } ?: this.setImageResource(R.drawable.img)
}