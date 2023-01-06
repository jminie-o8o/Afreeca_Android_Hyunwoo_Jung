package com.example.afreecaandroid.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.example.afreecaandroid.data.datasource.CookDataSource
import com.example.afreecaandroid.ui.model.UiData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CookRepositoryImpl @Inject constructor(
    private val cookDataSource: CookDataSource
) : CookRepository {
    override suspend fun getCategoryNum(): String {
        return cookDataSource.getCategoryNum()
    }

    override fun getTalkCamBroadCastList(categoryNum: String): Flow<PagingData<UiData>> {
        return cookDataSource.getTalkCamBroadCastList(categoryNum).map { pagingBroadList ->
            pagingBroadList.map { board ->
                UiData.toTalkCamDataFromApi(board)
            }
        }
    }
}
