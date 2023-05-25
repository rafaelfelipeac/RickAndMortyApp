package com.rafaelfelipeac.rickandmortyapp.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("BaseUrl")
    fun providesBaseUrl() = "https://rickandmortyapi.com/api/"

    @Provides
    @Singleton
    fun providesHttp(): OkHttpClient =
        OkHttpClient.Builder()
            //.readTimeout(60, TimeUnit.SECONDS)
            //.connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
}