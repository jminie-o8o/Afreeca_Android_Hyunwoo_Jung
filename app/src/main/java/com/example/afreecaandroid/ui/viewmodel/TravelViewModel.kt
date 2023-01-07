package com.example.afreecaandroid.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.afreecaandroid.data.repository.TravelRepository
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
class TravelViewModel @Inject constructor(
    private val travelRepository: TravelRepository
) : ViewModel() {

    private val _travelBroadCastList = MutableStateFlow<UiState<PagingData<UiData>>>(UiState.Loading)
    val travelBroadCastList: StateFlow<UiState<PagingData<UiData>>> = _travelBroadCastList.asStateFlow()

    private val _travelBroadCastDetail = MutableStateFlow<UiState<UiData>>(UiState.Loading)
    val travelBroadCastDetail: StateFlow<UiState<UiData>> = _travelBroadCastDetail.asStateFlow()

    private val _error = MutableSharedFlow<CEHModel>()
    val error: SharedFlow<CEHModel> = _error

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _error.emit(CoroutineException.handleThrowableWithCEHModel(throwable))
        }
    }

    init {
        getTravelBroadCastList()
    }

    private fun getTravelBroadCastList() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            travelRepository.getTalkCamBroadCastList()
                .cachedIn(viewModelScope)
                .catch { throwable ->
                    _travelBroadCastList.value = UiState.Error
                    _error.emit(CoroutineException.handleThrowableWithCEHModel(throwable))
                }
                .collect {
                    _travelBroadCastList.value = UiState.Success(it)
                }
        }
    }

    fun setTravelDetailData(uiData: UiData) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                _travelBroadCastDetail.value = UiState.Success(uiData)
            } catch (throwable: Exception) {
                _travelBroadCastDetail.value = UiState.Error
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
