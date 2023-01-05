package com.example.afreecaandroid.data.di

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppInterceptor @Inject constructor() : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
        val newRequest = request().newBuilder()
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .build()
        proceed(newRequest)
    }
}
