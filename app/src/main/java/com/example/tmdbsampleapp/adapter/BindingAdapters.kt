package com.example.tmdbsampleapp.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("imgFromUrl")
fun loadImageUrl(imageView: ImageView, url: String) {
    if (url.isNotEmpty())
        Glide.with(imageView)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)

}