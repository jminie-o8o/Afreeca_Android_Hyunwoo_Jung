package com.example.afreecaandroid.data.di

import com.example.afreecaandroid.data.datasource.TalkCamDataSource
import com.example.afreecaandroid.data.datasource.TalkCamDataSourceImpl
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
    abstract fun bindApiDataSource(
        talkCamDataSourceImpl: TalkCamDataSourceImpl
    ): TalkCamDataSource
}
