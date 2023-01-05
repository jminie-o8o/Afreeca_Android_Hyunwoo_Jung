package com.example.afreecaandroid.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.example.afreecaandroid.data.datasource.TalkCamDataSource
import com.example.afreecaandroid.ui.model.TalkCamData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TalkCamRepositoryImpl @Inject constructor(
    private val talkCamDataSource: TalkCamDataSource
): TalkCamRepository {

    override suspend fun getCategoryNum(): String {
        return talkCamDataSource.getCategoryNum()
    }

    override fun getTalkCamBroadCastList(categoryNum: String): Flow<PagingData<TalkCamData>> {
        return talkCamDataSource.getTalkCamBroadCastList(categoryNum).map { pagingBroadList ->
            pagingBroadList.map { board ->
                TalkCamData.toTalkCamDataFromApi(board)
            }
        }
    }
}
