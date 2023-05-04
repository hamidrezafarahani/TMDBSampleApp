package com.example.tmdbsampleapp.network

import com.example.tmdbsampleapp.network.apiresponse.ApiResponse
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okio.IOException
import java.lang.reflect.Type

object TypeConverter {

    fun <T> getResultAsList(type: Type, list: List<Any?>): List<T>? {
        return try {
            val gson = GsonBuilder().create()
            val json = gson.toJson(list)
            gson.fromJson(json, getListType(type))

        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun getListType(type: Type): Type {
        return TypeToken.getParameterized(List::class.java, type).type
    }

    fun <T> getResultAsObject(response: ApiResponse.ApiSuccessResponse<T>): T? {
        return try {
            val gson = GsonBuilder().create()
            gson.fromJson<T>(gson.toJson(response.body), response.dataType)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}