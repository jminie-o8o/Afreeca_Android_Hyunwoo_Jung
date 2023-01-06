package com.example.afreecaandroid.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.afreecaandroid.data.repository.TravelRepository
import com.example.afreecaandroid.ui.model.UiData
import com.example.afreecaandroid.uitl.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TravelViewModel @Inject constructor(
    private val travelRepository: TravelRepository
) : ViewModel() {

    private val _travelBroadCastList = MutableStateFlow<UiState<PagingData<UiData>>>(UiState.Loading)
    val travelBroadCastList: StateFlow<UiState<PagingData<UiData>>> = _travelBroadCastList.asStateFlow()

    fun getTravelBroadCastList() {
        viewModelScope.launch(Dispatchers.IO) {
            val categoryName = async {
                travelRepository.getCategoryNum()
            }
            travelRepository.getTalkCamBroadCastList(categoryName.await())
                .cachedIn(viewModelScope)
                .catch {
                    _travelBroadCastList.value = UiState.Error
                }
                .collect {
                    _travelBroadCastList.value = UiState.Success(it)
                }
        }
    }
}
