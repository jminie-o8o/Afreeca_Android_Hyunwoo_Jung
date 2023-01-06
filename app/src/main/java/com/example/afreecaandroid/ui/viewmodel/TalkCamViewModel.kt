package com.example.afreecaandroid.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.afreecaandroid.data.repository.TalkCamRepository
import com.example.afreecaandroid.ui.model.UiData
import com.example.afreecaandroid.uitl.CEHModel
import com.example.afreecaandroid.uitl.CoroutineException
import com.example.afreecaandroid.uitl.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TalkCamViewModel @Inject constructor(
    private val talkCamRepository: TalkCamRepository
) : ViewModel() {

    private val _talkCamBroadCastList = MutableStateFlow<UiState<PagingData<UiData>>>(UiState.Loading)
    val talkCamBroadCastList: StateFlow<UiState<PagingData<UiData>>> = _talkCamBroadCastList.asStateFlow()

    private val _talkCamBroadCastDetail = MutableStateFlow<UiState<UiData>>(UiState.Loading)
    val talkCamBroadCastDetail: StateFlow<UiState<UiData>> = _talkCamBroadCastDetail.asStateFlow()

    private val _error = MutableSharedFlow<CEHModel>()
    val error: SharedFlow<CEHModel> = _error

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _error.emit(CoroutineException.handleThrowableWithCEHModel(throwable))
        }
    }

    fun getTalkCamBroadCastList() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
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

    fun setTalkCamDetailData(uiData: UiData) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                _talkCamBroadCastDetail.value = UiState.Success(uiData)
            } catch (e: Exception) {
                _talkCamBroadCastDetail.value = UiState.Error
                throw e
            }
        }
    }

    fun handlePagingSourceError(throwable: Throwable) {
        viewModelScope.launch {
            _error.emit(CoroutineException.handleThrowableWithCEHModel(throwable))
        }
    }
}
