package com.oishikenko.android.recruitment.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.oishikenko.android.recruitment.data.CookingRecordsPagingSource
import com.oishikenko.android.recruitment.data.model.CookingRecord
import com.oishikenko.android.recruitment.data.repository.CookingRecordsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

    fun getSearchResultStream(query: String): Flow<PagingData<CookingRecord>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CookingRecordsPagingSource(cookingRecordsRepository) }
        ).flow
    }

    val items: Flow<PagingData<CookingRecord>> = Pager(
        config = PagingConfig(pageSize = 30, enablePlaceholders = false),
        pagingSourceFactory = { CookingRecordsPagingSource(cookingRecordsRepository) }
    )
        .flow
        .cachedIn(viewModelScope)
}