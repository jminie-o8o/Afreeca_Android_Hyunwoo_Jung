package com.example.afreecaandroid.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.afreecaandroid.data.api.TravelDataSourceApi
import com.example.afreecaandroid.data.model.Broad
import com.example.afreecaandroid.uitl.Constants

class TravelPagingSource(
    private val travelDataSourceApi: TravelDataSourceApi,
    private val categoryNum: String
) : PagingSource<Int, Broad>() {

    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, Broad> {
        return try {
            val pageNumber = params.key ?: STARTING_PAGE_INDEX

            val talkCamApiDTO = travelDataSourceApi.searchTravelBroadcast(Constants.CLIENT_ID, pageNumber)
            val broadList = talkCamApiDTO.broad

            val talkCamList = broadList.filter { broadList ->
                broadList.broadCateNo == categoryNum
            }

            val prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else talkCamApiDTO.pageNo - 1
            val nextKey = if (checkEndOfPaginationReached(
                    talkCamApiDTO.pageNo,
                    talkCamApiDTO.totalCnt,
                    Constants.PAGE_BLOCK
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

    override fun getRefreshKey(state: PagingState<Int, Broad>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    // 전체 데이터 개수인 totalCount 에서
    // 페이지 하나당 들어오는 개수인 pageBlock 을 나눈 몫에 1을 더하면 총 필요한 페이지 수가 된다.
    // 예를 들어 전체 데이터가 101개고 한번에 10개의 데이터가 들어온다면 필요한 페이지는 101 / 10 + 1 = 11페이지
    // 단 전체 데이터에서 페이지당 데이터를 나누었을 때 나머지가 0이라면 나눈 몫이 필요한 페이지다. 즉 1을 더할 필요 X
    // 추가 - Response 를 확인하니 pageNo 가 항상 0으로 넘어오는데 실제로는 60개가 넘어오므로 pageBlock 60 으로 고정
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
