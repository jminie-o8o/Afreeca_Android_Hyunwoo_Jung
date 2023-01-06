package com.example.afreecaandroid.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.afreecaandroid.data.api.TravelDataSourceApi
import com.example.afreecaandroid.data.model.Broad
import com.example.afreecaandroid.data.pagingsource.TravelPagingSource
import com.example.afreecaandroid.data.session.CategorySession
import com.example.afreecaandroid.uitl.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TravelDataSourceImpl @Inject constructor(
    private val travelDataSourceApi: TravelDataSourceApi,
    private val categorySession: CategorySession
) : TravelDataSource {

    override fun getTalkCamBroadCastList(): Flow<PagingData<Broad>> {
        val categoryNum = categorySession.categoryApiDTO .filter {
            it.cateName == Constants.TRAVEL
        }[0].cateNo

        val pagingSourceFactory = { TravelPagingSource(travelDataSourceApi, categoryNum) }

        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = false,
                maxSize = Constants.PAGE_SIZE * 3
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}
