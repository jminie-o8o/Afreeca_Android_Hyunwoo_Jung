package com.example.afreecaandroid.data.repository

import androidx.paging.PagingData
import com.example.afreecaandroid.ui.model.UiData
import kotlinx.coroutines.flow.Flow

interface TravelRepository {

    fun getTalkCamBroadCastList(): Flow<PagingData<UiData>>
}
