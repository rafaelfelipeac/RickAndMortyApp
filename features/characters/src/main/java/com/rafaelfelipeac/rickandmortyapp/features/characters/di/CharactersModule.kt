package com.rafaelfelipeac.rickandmortyapp.features.characters.di

import com.rafaelfelipeac.rickandmortyapp.features.characters.data.CharactersRepository
import com.rafaelfelipeac.rickandmortyapp.features.characters.data.CharactersRepositoryImpl
import com.rafaelfelipeac.rickandmortyapp.features.characters.data.CharactersService
import com.rafaelfelipeac.rickandmortyapp.features.characters.domain.CharactersInteractor
import com.rafaelfelipeac.rickandmortyapp.features.characters.domain.CharactersInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharactersModule {

    @Provides
    @Singleton
    fun providesCharactersService(): CharactersService = Retrofit.Builder()
        .baseUrl("https://google.com.br")
        .build()
        .create(CharactersService::class.java)

    @Provides
    @Singleton
    fun provideCharactersRepository(
        service: CharactersService
    ) : CharactersRepository = CharactersRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideCharactersInteractor(
        repository: CharactersRepository
    ) : CharactersInteractor = CharactersInteractorImpl(repository)
}
