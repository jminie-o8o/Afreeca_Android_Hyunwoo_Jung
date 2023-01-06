package com.example.afreecaandroid.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.afreecaandroid.data.api.CookDataSourceApi
import com.example.afreecaandroid.data.model.Broad
import com.example.afreecaandroid.data.pagingsource.CookPagingSource
import com.example.afreecaandroid.data.session.CategorySession
import com.example.afreecaandroid.uitl.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CookDataSourceImpl @Inject constructor(
    private val cookDataSourceApi: CookDataSourceApi,
    private val categorySession: CategorySession
) : CookDataSource {

    override fun getCookBroadCastList(): Flow<PagingData<Broad>> {
        val categoryNum = categorySession.categoryApiDTO .filter {
            it.cateName == Constants.COOK
        }[0].cateNo

        val pagingSourceFactory = { CookPagingSource(cookDataSourceApi, categoryNum) }

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
