package com.example.afreecaandroid.data.repository

import androidx.paging.PagingData
import com.example.afreecaandroid.ui.model.UiData
import kotlinx.coroutines.flow.Flow

interface TalkCamRepository {

    suspend fun getCategoryNum()

    fun getTalkCamBroadCastList(): Flow<PagingData<UiData>>
}
