package com.example.afreecaandroid.data.api

import com.example.afreecaandroid.data.model.DataSourceDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface CookDataSourceApi {

    @GET("broad/list")
    suspend fun searchCookBroadcast(
        @Query("client_id") clientId: String,
        @Query("page_no") pageNum: Int
    ): DataSourceDTO
}
