package com.example.afreecaandroid.data.di

import com.example.afreecaandroid.data.repository.TalkCamRepository
import com.example.afreecaandroid.data.repository.TalkCamRepositoryImpl
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
    abstract fun bindMainRepository(
        talkCamRepositoryImpl: TalkCamRepositoryImpl
    ): TalkCamRepository
}
