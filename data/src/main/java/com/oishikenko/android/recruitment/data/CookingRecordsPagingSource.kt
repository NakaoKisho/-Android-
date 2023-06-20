package com.oishikenko.android.recruitment.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.oishikenko.android.recruitment.data.model.CookingRecord
import com.oishikenko.android.recruitment.data.repository.CookingRecordsRepository
import kotlinx.coroutines.flow.map

private const val STARTING_KEY = 0

class CookingRecordsPagingSource(
    private var cookingRecordsRepository: CookingRecordsRepository
): PagingSource<Int, CookingRecord>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CookingRecord> {
        val start = params.key ?: STARTING_KEY
        var cookingRecordList: List<CookingRecord> = emptyList()
        cookingRecordsRepository
            .getCookingRecords(offset = 0, limit = 30)
            .map {
                cookingRecordList = it.body()?.cookingRecords ?: emptyList()
            }
        val prevKey = if (start == STARTING_KEY) {
            null
        } else {
            start - 1
        }
        val nextKey = if (cookingRecordList.isEmpty()) {
            null
        } else {
            30
        }

        return LoadResult.Page(
            data = cookingRecordList,
            prevKey = prevKey,
            nextKey = nextKey
        )
    }

    override fun getRefreshKey(state: PagingState<Int, CookingRecord>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}