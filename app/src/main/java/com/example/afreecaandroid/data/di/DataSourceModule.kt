package com.example.afreecaandroid.data.di

import com.example.afreecaandroid.data.datasource.TalkCamDataSource
import com.example.afreecaandroid.data.datasource.TalkCamDataSourceImpl
import com.example.afreecaandroid.data.datasource.TravelDataSource
import com.example.afreecaandroid.data.datasource.TravelDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindTalkCamDataSource(
        talkCamDataSourceImpl: TalkCamDataSourceImpl
    ): TalkCamDataSource

    @Singleton
    @Binds
    abstract fun bindTravelDataSource(
        travelDataSourceImpl: TravelDataSourceImpl
    ): TravelDataSource
}
