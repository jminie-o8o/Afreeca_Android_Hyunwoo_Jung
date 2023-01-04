package com.example.afreecaandroid.data.repository

import androidx.paging.PagingData
import com.example.afreecaandroid.data.datasource.TalkCamDataSource
import com.example.afreecaandroid.ui.model.TalkCamData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TalkCamRepositoryImpl @Inject constructor(
    private val talkCamDataSource: TalkCamDataSource
): TalkCamRepository {

    override fun getTalkCamBroadCastList(): Flow<PagingData<TalkCamData>> {
        TODO("Not yet implemented")
    }
}
