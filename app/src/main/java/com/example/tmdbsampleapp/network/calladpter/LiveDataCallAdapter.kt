package com.example.tmdbsampleapp.network.calladpter

import androidx.lifecycle.LiveData
import com.example.tmdbsampleapp.network.apiresponse.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

typealias LiveDataResponse = LiveData<ApiResponse<Map<String, String>>>

class LiveDataCallAdapter(private val dataType: Type) :
    CallAdapter<Map<String, String>, LiveDataResponse> {
    override fun responseType(): Type {
        return dataType
    }

    override fun adapt(call: Call<Map<String, String>>): LiveDataResponse {
        return object : LiveDataResponse() {

            private var started = AtomicBoolean(false)

            override fun onActive() {
                super.onActive()

                if (started.compareAndSet(false, true)) {

                    call.enqueue(object : Callback<Map<String, String>> {
                        override fun onResponse(
                            call: Call<Map<String, String>>,
                            response: Response<Map<String, String>>
                        ) {
                            postValue(ApiResponse.create(response, dataType))
                        }

                        override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                            postValue(ApiResponse.create(t))
                        }
                    })
                }
            }
        }
    }
}