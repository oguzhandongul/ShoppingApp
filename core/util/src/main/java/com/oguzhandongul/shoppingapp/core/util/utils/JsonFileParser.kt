package com.oguzhandongul.shoppingapp.core.util.utils

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class JsonFileParser @Inject constructor(@ApplicationContext val context: Context, val moshi: Moshi) {

    inline fun <reified T> parseRawFileToObject(resourceId: Int): T? {
        val jsonString = readRawFile(resourceId) ?: return null
        val adapter: JsonAdapter<T> = moshi.adapter(T::class.java)
        return adapter.fromJson(jsonString)
    }

    inline fun <reified T> parseRawFileToList(resourceId: Int): List<T>? {
        val jsonString = readRawFile(resourceId) ?: return null
        val type = Types.newParameterizedType(List::class.java, T::class.java)
        val adapter: JsonAdapter<List<T>> = moshi.adapter(type)
        return adapter.fromJson(jsonString)
    }

    fun readRawFile(resourceId: Int): String? {
        return try {
            context.resources.openRawResource(resourceId).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            // Handle exceptions to make it customize
            null
        }
    }
}
