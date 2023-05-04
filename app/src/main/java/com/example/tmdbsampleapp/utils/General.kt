package com.example.tmdbsampleapp.utils

import android.content.Context
import android.widget.Toast

infix fun Context.log(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}