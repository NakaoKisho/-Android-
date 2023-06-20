package com.oishikenko.android.recruitment.feature.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oishikenko.android.recruitment.data.model.CookingRecord
import com.oishikenko.android.recruitment.data.model.CookingRecords
import com.oishikenko.android.recruitment.data.repository.CookingRecordsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private var cookingRecordsRepository: CookingRecordsRepository
) : ViewModel() {
    val cookingRecords: StateFlow<List<CookingRecord>> =
        cookingRecordsRepository.getCookingRecords(offset = 0, limit = 30).map {
            it.body()?.cookingRecords ?: emptyList<CookingRecord>()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList<CookingRecord>()
        )

    val cookingRecordList = mutableStateListOf<CookingRecord?>()
    private var page by mutableStateOf(30)
    var canPaginate by mutableStateOf(false)
    var listState by mutableStateOf(ListState.IDLE)

    init {
        getCookingRecord()
    }

    fun getCookingRecord() = viewModelScope.launch {
//        if (page == 30 || (page != 30 && canPaginate) && listState == ListState.IDLE) {
            listState = if (page == 30) ListState.LOADING else ListState.PAGINATING

            var totalPageCount = 0
            cookingRecordsRepository
                .getCookingRecords(0, page)
                .map {
                    it.body()?.cookingRecords ?: emptyList<CookingRecord>()
                }
                .collect {
                    if (page == 30) {
                        cookingRecordList.clear()
                        cookingRecordList.addAll(it)
                    } else {
                        cookingRecordList.addAll(it)
                    }

                    listState = ListState.IDLE

                    if (canPaginate) page++
                }
//        }
    }

    override fun onCleared() {
        page = 1
        listState = ListState.IDLE
        canPaginate = false
        super.onCleared()
    }
}

enum class ListState {
    IDLE,
    LOADING,
    PAGINATING,
    ERROR,
    PAGINATION_EXHAUST,
}