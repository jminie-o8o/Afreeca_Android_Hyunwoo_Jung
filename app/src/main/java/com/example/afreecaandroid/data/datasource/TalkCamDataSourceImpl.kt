package com.example.afreecaandroid.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.afreecaandroid.data.api.CategoryApi
import com.example.afreecaandroid.data.api.TalkCamDataSourceApi
import com.example.afreecaandroid.data.model.TalkCamApiDTO
import com.example.afreecaandroid.data.pagingsource.TalkCamPagingSource
import com.example.afreecaandroid.uitl.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TalkCamDataSourceImpl @Inject constructor(
    private val talkCamDataSourceApi: TalkCamDataSourceApi,
    private val categoryApi: CategoryApi
): TalkCamDataSource {

    override fun getTalkCamBroadCastList(): Flow<PagingData<TalkCamApiDTO.Broad>> {
        val pagingSourceFactory = { TalkCamPagingSource(talkCamDataSourceApi, categoryApi) }

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
