package com.example.afreecaandroid.data.datasource

import androidx.paging.PagingData
import com.example.afreecaandroid.data.model.TalkCamApiDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class TalkCamDataSourceImpl: TalkCamDataSource {

    override fun getTalkCamBroadCastList(): Flow<PagingData<TalkCamApiDTO>> {
        TODO("Not yet implemented")
    }
}
