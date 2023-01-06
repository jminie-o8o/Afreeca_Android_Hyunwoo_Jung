package com.example.afreecaandroid.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.afreecaandroid.data.repository.TalkCamRepository
import com.example.afreecaandroid.ui.model.TalkCamData
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
class TalkCamViewModel @Inject constructor(
    private val talkCamRepository: TalkCamRepository
) : ViewModel() {

    private val _talkCamBroadCastList = MutableStateFlow<UiState<PagingData<TalkCamData>>>(UiState.Loading)
    val talkCamBroadCastList: StateFlow<UiState<PagingData<TalkCamData>>> = _talkCamBroadCastList.asStateFlow()

    private val _talkCamBroadCastDetail = MutableStateFlow<UiState<TalkCamData>>(UiState.Loading)
    val talkCamBroadCastDetail: StateFlow<UiState<TalkCamData>> = _talkCamBroadCastDetail.asStateFlow()

    fun getTalkCamBroadCastList() {
        viewModelScope.launch(Dispatchers.IO) {
            val categoryName = async {
                talkCamRepository.getCategoryNum()
            }
            talkCamRepository.getTalkCamBroadCastList(categoryName.await())
                .cachedIn(viewModelScope)
                .catch {
                    _talkCamBroadCastList.value = UiState.Error
                }
                .collect {
                    _talkCamBroadCastList.value = UiState.Success(it)
                }
        }
    }

    fun setTalkCamDetailData(talkCamData: TalkCamData) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _talkCamBroadCastDetail.value = UiState.Success(talkCamData)
            } catch (e: Exception) {
                _talkCamBroadCastDetail.value = UiState.Error
                throw e
            }
        }
    }
}
