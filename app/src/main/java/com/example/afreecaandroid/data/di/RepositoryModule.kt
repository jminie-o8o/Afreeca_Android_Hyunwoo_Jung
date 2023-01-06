package com.example.afreecaandroid.data.di

import com.example.afreecaandroid.data.repository.TalkCamRepository
import com.example.afreecaandroid.data.repository.TalkCamRepositoryImpl
import com.example.afreecaandroid.data.repository.TravelRepository
import com.example.afreecaandroid.data.repository.TravelRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTalkCamRepository(
        talkCamRepositoryImpl: TalkCamRepositoryImpl
    ): TalkCamRepository

    @Singleton
    @Binds
    abstract fun bindTravelRepository(
        travelRepositoryImpl: TravelRepositoryImpl
    ): TravelRepository
}
