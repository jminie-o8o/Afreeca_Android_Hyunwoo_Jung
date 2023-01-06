package com.example.afreecaandroid.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.example.afreecaandroid.data.datasource.TravelDataSource
import com.example.afreecaandroid.ui.model.UiData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TravelRepositoryImpl @Inject constructor(
    private val travelDataSource: TravelDataSource
) : TravelRepository {

    override fun getTalkCamBroadCastList(): Flow<PagingData<UiData>> {
        return travelDataSource.getTalkCamBroadCastList().map { pagingBroadList ->
            pagingBroadList.map { board ->
                UiData.toTalkCamDataFromApi(board)
            }
        }
    }
}
