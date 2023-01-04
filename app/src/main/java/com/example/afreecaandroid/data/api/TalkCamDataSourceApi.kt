package com.example.afreecaandroid.data.api

import com.example.afreecaandroid.data.model.TalkCamApiDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface TalkCamDataSourceApi {

    @GET("broad/list")
    suspend fun searchUser(
        @Query("client_id") clientId: String
    ): TalkCamApiDTO
}
