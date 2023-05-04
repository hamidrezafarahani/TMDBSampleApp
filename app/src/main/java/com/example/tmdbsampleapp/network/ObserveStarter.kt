package com.example.tmdbsampleapp.network

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.tmdbsampleapp.network.apierror.ApiError

typealias OnSuccessCallback<T> = (T) -> Unit
typealias OnFailedCallback = (ApiError) -> Unit

class CallbackStack<T> {

    var onSuccess: (OnSuccessCallback<T>)? = null
    var onFailed: (OnFailedCallback)? = null
    var doFinally: (() -> Unit)? = null
    var onLoading: (() -> Unit)? = null

    fun onSuccess(cb: OnSuccessCallback<T>) {
        this.onSuccess = cb
    }

    fun onFailed(cb: OnFailedCallback) {
        this.onFailed = cb
    }

    fun onLoading(cb: () -> Unit) {
        this.onLoading = cb
    }

    fun doFinally(cb: () -> Unit) {
        this.doFinally = cb
    }
}

class ObserveStarter<T>(
    private val owner: LifecycleOwner,
    private val livedata: LiveData<Resource<T>>
) {
    var isStarted = false

    fun callbacks(action: CallbackStack<T>.() -> Unit) {
        val stack = CallbackStack<T>().apply { action() }
        livedata.observe(owner) {
            when (it) {
                is Resource.Loading -> {
                    isStarted = true
                    stack.onLoading?.invoke()
                }

                is Resource.Success -> {
                    it.data?.let { data -> stack.onSuccess?.invoke(data) }
                }

                is Resource.Failure -> {
                    if (isStarted) stack.onFailed?.invoke(it.error)
                }
            }

            if (it !is Resource.Loading && isStarted) {
                isStarted = false
                stack.doFinally?.invoke()
            }
        }
    }
}