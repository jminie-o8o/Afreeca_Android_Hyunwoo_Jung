package com.example.afreecaandroid.data.repository

import androidx.paging.PagingData
import com.example.afreecaandroid.ui.model.TalkCamData
import kotlinx.coroutines.flow.Flow

interface TalkCamRepository {

    suspend fun getCategoryNum(): String

    fun getTalkCamBroadCastList(categoryNum: String): Flow<PagingData<TalkCamData>>
}
