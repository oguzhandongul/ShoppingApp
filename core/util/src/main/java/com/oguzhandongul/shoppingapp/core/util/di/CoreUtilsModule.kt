package com.oguzhandongul.shoppingapp.core.util.di

import android.content.Context
import com.oguzhandongul.shoppingapp.core.util.utils.JsonFileParser
import com.oguzhandongul.shoppingapp.core.util.utils.ResourceHelper
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun provideResourceHelper(@ApplicationContext context: Context): ResourceHelper {
        return ResourceHelper(context = context)
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory())  // Add this line
        .build()

    @Provides
    @Singleton
    fun provideJsonParser(@ApplicationContext context: Context, moshi: Moshi) =
        JsonFileParser(context = context, moshi = moshi)

}