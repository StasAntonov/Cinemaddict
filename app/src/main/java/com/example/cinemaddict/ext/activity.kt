package com.example.cinemaddict.ext

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}