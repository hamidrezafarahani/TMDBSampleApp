package com.example.tmdbsampleapp.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.tmdbsampleapp.network.ObserveStarter
import com.example.tmdbsampleapp.network.Resource

fun <T> LiveData<Resource<T>>.with(owner: LifecycleOwner): ObserveStarter<T> {
    return ObserveStarter(owner, this)
}



