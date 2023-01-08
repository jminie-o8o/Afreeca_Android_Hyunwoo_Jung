package com.example.afreecaandroid.data.api

import com.example.afreecaandroid.data.model.CategoryApiDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryApiTest {

    @GET("broad/category/list")
    suspend fun getCategory(
        @Query("client_id") clientId: String
    ): Response<CategoryApiDTO>
}
