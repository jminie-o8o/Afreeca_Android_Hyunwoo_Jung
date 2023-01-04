package com.example.afreecaandroid.data.datasource

import androidx.paging.PagingData
import com.example.afreecaandroid.data.model.TalkCamApiDTO
import kotlinx.coroutines.flow.Flow

interface TalkCamDataSource {

    fun getTalkCamBroadCastList(): Flow<PagingData<TalkCamApiDTO>>
}
