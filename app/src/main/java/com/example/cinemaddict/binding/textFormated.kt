package com.example.cinemaddict.binding


import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.cinemaddict.domain.entity.FilmDiscoverData

@BindingAdapter("app:itemDiscoverScreenTextFormatter")
fun TextView.itemDiscoverScreenTextFormatter(viewData: FilmDiscoverData) {

    viewData.releaseDate.let {
        val formattedText: String = if (it == null) {
            viewData.title
        } else {
            "${viewData.title} (${it.year})"
        }
        text = formattedText
    }
}