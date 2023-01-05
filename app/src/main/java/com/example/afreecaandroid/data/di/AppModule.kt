package com.example.afreecaandroid.data.di

import com.example.afreecaandroid.data.api.CategoryApi
import com.example.afreecaandroid.data.api.TalkCamDataSourceApi
import com.example.afreecaandroid.uitl.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // AppModule 은 앱 전체에서 사용할 모듈이기 때문에 SingletonComponent 에 설치
object AppModule {

    // Retrofit
    @Singleton
    @Provides
    fun provideOkHttpClient(appInterceptor: AppInterceptor): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(appInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideTalkCAmApiService(retrofit: Retrofit): TalkCamDataSourceApi {
        return retrofit.create(TalkCamDataSourceApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCategoryService(retrofit: Retrofit): CategoryApi {
        return retrofit.create(CategoryApi::class.java)
    }
}
