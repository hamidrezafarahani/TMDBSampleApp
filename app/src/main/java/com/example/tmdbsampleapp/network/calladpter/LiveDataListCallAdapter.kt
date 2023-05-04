package com.example.tmdbsampleapp.network.calladpter

import androidx.lifecycle.LiveData
import com.example.tmdbsampleapp.data.remote.dto.TMDBResponse
import com.example.tmdbsampleapp.network.apiresponse.ApiListResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

typealias LiveDataListResponse = LiveData<ApiListResponse<TMDBResponse<*>>>

class LiveDataListCallAdapter(private val dataType: Type) :
    CallAdapter<TMDBResponse<*>, LiveDataListResponse> {
    override fun responseType(): Type {
        return TMDBResponse::class.java
    }

    override fun adapt(call: Call<TMDBResponse<*>>): LiveDataListResponse {
        return object : LiveDataListResponse() {

            private var started = AtomicBoolean(false)

            override fun onActive() {
                super.onActive()

                if (started.compareAndSet(false, true)) {

                    call.enqueue(object : Callback<TMDBResponse<*>> {
                        override fun onResponse(
                            call: Call<TMDBResponse<*>>,
                            response: Response<TMDBResponse<*>>
                        ) {
                            postValue(ApiListResponse.create(response, dataType))
                        }

                        override fun onFailure(call: Call<TMDBResponse<*>>, t: Throwable) {
                            postValue(ApiListResponse.create(t))
                        }
                    })
                }
            }
        }
    }
}