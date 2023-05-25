package com.rafaelfelipeac.characterdetail.di

import com.rafaelfelipeac.characterdetail.data.CharacterDetailRepository
import com.rafaelfelipeac.characterdetail.data.CharacterDetailRepositoryImpl
import com.rafaelfelipeac.characterdetail.data.CharacterDetailService
import com.rafaelfelipeac.characterdetail.domain.CharacterDetailInteractor
import com.rafaelfelipeac.characterdetail.domain.CharacterDetailInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharacterDetailModule {

    @Provides
    @Singleton
    fun providesCharacterDetailService(
        okHttpClient: OkHttpClient,
        @Named("BaseUrl") baseUrl: String
    ): CharacterDetailService = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(CharacterDetailService::class.java)

    @Provides
    @Singleton
    fun provideCharacterDetailRepository(
        service: CharacterDetailService
    ) : CharacterDetailRepository = CharacterDetailRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideCharacterDetailInteractor(
        repository: CharacterDetailRepository
    ) : CharacterDetailInteractor = CharacterDetailInteractorImpl(repository)
}