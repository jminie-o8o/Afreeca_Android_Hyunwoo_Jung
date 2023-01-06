package com.example.afreecaandroid.data.repository

import androidx.paging.PagingData
import com.example.afreecaandroid.ui.model.UiData
import kotlinx.coroutines.flow.Flow

interface CookRepository {

    suspend fun getCategoryNum(): String

    fun getCookBroadCastList(categoryNum: String): Flow<PagingData<UiData>>
}
