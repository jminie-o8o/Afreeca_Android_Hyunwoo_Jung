package com.example.afreecaandroid.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.afreecaandroid.data.api.CategoryApi
import com.example.afreecaandroid.data.api.TalkCamDataSourceApi
import com.example.afreecaandroid.data.model.Broad
import com.example.afreecaandroid.data.pagingsource.TalkCamPagingSource
import com.example.afreecaandroid.data.session.CategorySession
import com.example.afreecaandroid.uitl.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TalkCamDataSourceImpl @Inject constructor(
    private val talkCamDataSourceApi: TalkCamDataSourceApi,
    private val categoryApi: CategoryApi,
    private val categorySession: CategorySession
) : TalkCamDataSource {

    override suspend fun getCategoryNum() {
        val broadCategoryList = categoryApi.getCategory(Constants.CLIENT_ID).broadCategory
        categorySession.categoryApiDTO = broadCategoryList
    }

    override fun getTalkCamBroadCastList(): Flow<PagingData<Broad>> {
        val categoryNum = categorySession.categoryApiDTO .filter {
            it.cateName == Constants.TALK_CAM
        }[0].cateNo

        val pagingSourceFactory = { TalkCamPagingSource(talkCamDataSourceApi, categoryNum) }

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
