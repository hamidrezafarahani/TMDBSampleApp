package com.example.tmdbsampleapp.network.calladpter

import androidx.lifecycle.LiveData
import com.example.tmdbsampleapp.network.apiresponse.ApiListResponse
import com.example.tmdbsampleapp.network.apiresponse.ApiResponse
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.inject.Inject

class LiveDataCallAdapterFactory @Inject constructor() : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        if (getRawType(returnType) != LiveData::class.java) return null

        require(returnType is ParameterizedType) {
            "returnType must be parameterized"
        }
        val observableType = getParameterUpperBound(0, returnType)
        require(observableType is ParameterizedType) {
            "returnType must be parameterized"
        }

        val dataType = getParameterUpperBound(0, observableType)

        return when (getRawType(observableType)) {
            ApiResponse::class.java -> LiveDataCallAdapter(dataType)
            ApiListResponse::class.java -> LiveDataListCallAdapter(dataType)
            else -> throw Exception("type must be a ApiResponse or ApiListResponse")
        }
    }
}