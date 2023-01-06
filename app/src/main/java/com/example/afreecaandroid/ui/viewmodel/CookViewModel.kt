package com.example.afreecaandroid.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.afreecaandroid.data.repository.CookRepository
import com.example.afreecaandroid.ui.model.UiData
import com.example.afreecaandroid.uitl.CEHModel
import com.example.afreecaandroid.uitl.CoroutineException
import com.example.afreecaandroid.uitl.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
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

    private val _error = MutableSharedFlow<CEHModel>()
    val error: SharedFlow<CEHModel> = _error

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _error.emit(CoroutineException.handleThrowableWithCEHModel(throwable))
        }
    }

    fun getCookBroadCastList() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            cookRepository.getCookBroadCastList()
                .cachedIn(viewModelScope)
                .catch { throwable ->
                    _cookBroadCastList.value = UiState.Error
                    _error.emit(CoroutineException.handleThrowableWithCEHModel(throwable))
                }
                .collect {
                    _cookBroadCastList.value = UiState.Success(it)
                }
        }
    }

    fun setCookDetailData(uiData: UiData) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                _cookBroadCastDetail.value = UiState.Success(uiData)
            } catch (throwable: Exception) {
                _cookBroadCastDetail.value = UiState.Error
                _error.emit(CoroutineException.handleThrowableWithCEHModel(throwable))
            }
        }
    }

    fun handlePagingSourceError(throwable: Throwable) {
        viewModelScope.launch {
            _error.emit(CoroutineException.handleThrowableWithCEHModel(throwable))
        }
    }
}
