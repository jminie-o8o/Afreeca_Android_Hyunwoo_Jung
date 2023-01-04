package com.example.afreecaandroid.data.repository

import androidx.paging.PagingData
import com.example.afreecaandroid.ui.model.TalkCamData
import kotlinx.coroutines.flow.Flow

interface TalkCamRepository {

    fun getTalkCamBroadCastList(): Flow<PagingData<TalkCamData>>
}
