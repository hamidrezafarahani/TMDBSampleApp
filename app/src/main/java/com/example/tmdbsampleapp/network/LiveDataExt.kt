package com.example.tmdbsampleapp.network

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T> LiveData<Resource<T>>.with(owner: LifecycleOwner): ObserveStarter<T> {
    return ObserveStarter(owner, this)
}



