package com.example.tmdbsampleapp.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.tmdbsampleapp.R

@BindingAdapter("imgFromUrl")
fun ImageView.loadImageUrl(url: String?) = url?.let {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_launcher_foreground)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

@BindingAdapter("isViewVisible")
fun View.isVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("swipeRefreshColors")
fun SwipeRefreshLayout.setSwipeRefreshColors(colorResIds: IntArray) {
    setColorSchemeColors(*colorResIds)
}