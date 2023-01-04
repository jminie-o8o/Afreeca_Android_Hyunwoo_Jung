package com.example.afreecaandroid.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.afreecaandroid.data.api.CategoryApi
import com.example.afreecaandroid.data.api.TalkCamDataSourceApi
import com.example.afreecaandroid.data.model.TalkCamApiDTO
import com.example.afreecaandroid.uitl.Constants

class TalkCamPagingSource(
    private val talkCamDataSourceApi: TalkCamDataSourceApi,
    private val categoryApi: CategoryApi
) : PagingSource<Int, TalkCamApiDTO.Broad>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TalkCamApiDTO.Broad> {
        return try {
            val pageNumber = params.key ?: STARTING_PAGE_INDEX

            val talkCamApiDTO = talkCamDataSourceApi.searchUser(Constants.CLIENT_ID)
            val broadList = talkCamApiDTO.broad
            val categoryList = categoryApi.getCategory(Constants.CLIENT_ID).broadCategory

            var talkCamCategoryNum = "emptyString"
            for (i in categoryList.indices) {
                if (categoryList[i].cateName == "토크/캠방") talkCamCategoryNum = categoryList[i].cateNo
            }
            val talkCamList = broadList.filter { broadList ->
                broadList.broadCateNo == talkCamCategoryNum
            }

            val prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else talkCamApiDTO.pageNo - 1
            val nextKey = if (checkEndOfPaginationReached(
                    talkCamApiDTO.pageNo,
                    talkCamApiDTO.totalCnt,
                    talkCamApiDTO.pageBlock
                )
            ) {
                null
            } else {
                talkCamApiDTO.pageNo + 1
            }
            LoadResult.Page(
                data = talkCamList,
                prevKey = prevKey,
                nextKey = nextKey,
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TalkCamApiDTO.Broad>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    // 전체 데이터 개수인 totalCount 에서
    // 페이지 하나당 들어오는 개수인 pageBlock 을 나눈 몫에 1을 더하면 총 필요한 페이지 수가 된다.
    // 예를 들어 전체 데이터가 101개고 한번에 10개의 데이터가 들어온다면 필요한 페이지는 101 / 10 + 1 = 11페이지
    // 단 전체 데이터에서 페이지당 데이터를 나누었을 때 나머지가 0이라면 나눈 몫이 필요한 페이지다. 즉 1을 더할 필요 X
    private fun checkEndOfPaginationReached(
        pageNumber: Int,
        totalCount: Int,
        pageBlock: Int
    ): Boolean {
        val checkNum = totalCount / pageBlock
        val formula = totalCount % pageBlock
        if (formula == 0) {
            if (pageNumber >= checkNum) return true
        } else {
            if (pageNumber >= checkNum + 1) return true
        }
        return false
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }
}
