package com.example.afreecaandroid.data.repository

import androidx.paging.PagingData
import com.example.afreecaandroid.ui.model.TalkCamData
import kotlinx.coroutines.flow.Flow

class TalkCamRepositoryImpl: TalkCamRepository {

    override fun getTalkCamBroadCastList(): Flow<PagingData<TalkCamData>> {
        TODO("Not yet implemented")
    }
}
