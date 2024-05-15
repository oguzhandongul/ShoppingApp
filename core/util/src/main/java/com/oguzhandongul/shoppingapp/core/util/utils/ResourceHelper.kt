package com.oguzhandongul.shoppingapp.core.util.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStream
import javax.inject.Inject

class ResourceHelper @Inject constructor(@ApplicationContext private val context: Context) {

    fun getString(resId: Int): String {
        return context.resources.getString(resId)
    }

    fun getStringArray(resId: Int): List<String> {
        return context.resources.getStringArray(resId).asList()
    }

    fun getRaw(resId: Int): InputStream {
        return context.resources.openRawResource(resId)
    }

    // Optionally we can add other methods for accessing different resources like drawables, colors, etc.
}