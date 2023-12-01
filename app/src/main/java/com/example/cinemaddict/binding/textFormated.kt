package com.example.cinemaddict.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.cinemaddict.domain.entity.FilmDiscoverData


@BindingAdapter("setFormattedText")
fun setFormattedText(textView: TextView, viewData: FilmDiscoverData) {

    val formattedText: String = if (viewData.releaseDate == null) {
        viewData.title
    } else {
        "${viewData.title} (${viewData.releaseDate.take(4)})"
    }

    textView.text = formattedText
}