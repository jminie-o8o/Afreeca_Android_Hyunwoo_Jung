package com.example.afreecaandroid.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.afreecaandroid.data.repository.CookRepository
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
class CookViewModel @Inject constructor(
    private val cookRepository: CookRepository
) : ViewModel() {

    private val _cookBroadCastList = MutableStateFlow<UiState<PagingData<UiData>>>(UiState.Loading)
    val cookBroadCastList: StateFlow<UiState<PagingData<UiData>>> = _cookBroadCastList.asStateFlow()

    private val _cookBroadCastDetail = MutableStateFlow<UiState<UiData>>(UiState.Loading)
    val cookBroadCastDetail: StateFlow<UiState<UiData>> = _cookBroadCastDetail.asStateFlow()

    fun getCookBroadCastList() {
        viewModelScope.launch(Dispatchers.IO) {
            val categoryName = async {
                cookRepository.getCategoryNum()
            }
            cookRepository.getCookBroadCastList(categoryName.await())
                .cachedIn(viewModelScope)
                .catch {
                    _cookBroadCastList.value = UiState.Error
                }
                .collect {
                    _cookBroadCastList.value = UiState.Success(it)
                }
        }
    }

    fun setCookDetailData(uiData: UiData) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _cookBroadCastDetail.value = UiState.Success(uiData)
            } catch (e: Exception) {
                _cookBroadCastDetail.value = UiState.Error
                throw e
            }
        }
    }
}
