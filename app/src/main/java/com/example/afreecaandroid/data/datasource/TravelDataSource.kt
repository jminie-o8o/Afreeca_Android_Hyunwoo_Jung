package com.example.afreecaandroid.data.datasource

import androidx.paging.PagingData
import com.example.afreecaandroid.data.model.Broad
import kotlinx.coroutines.flow.Flow

interface TravelDataSource {

    fun getTalkCamBroadCastList(): Flow<PagingData<Broad>>
}
