package com.example.afreecaandroid.data.datasource

import androidx.paging.PagingData
import com.example.afreecaandroid.data.model.Broad
import kotlinx.coroutines.flow.Flow

interface TalkCamDataSource {

    suspend fun getCategoryNum(): String

    fun getTalkCamBroadCastList(categoryNum: String): Flow<PagingData<Broad>>
}
