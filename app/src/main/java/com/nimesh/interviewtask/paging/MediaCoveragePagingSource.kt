package com.nimesh.interviewtask.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nimesh.interviewtask.data.model.MediaCoverages
import com.nimesh.interviewtask.data.model.MediaCoveragesItem
import com.nimesh.interviewtask.data.network.MediaCoverageServices

/**
 * Created by Nimesh Patel on 4/16/2024.
 * Purpose:
 */
class MediaCoveragePagingSource(private val apiService: MediaCoverageServices) :
    PagingSource<Int, MediaCoveragesItem>() {

    override fun getRefreshKey(state: PagingState<Int, MediaCoveragesItem>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaCoveragesItem> {
        return try {
            val nextPageNumber = params.key ?: 0
            val response = apiService.getMediaCoveragesAsync(params.loadSize, nextPageNumber)
            Log.e("neem", "load: nextPageNumber : $nextPageNumber", )
            response.forEach {
                Log.e("neem", "load: title : ${it.title}", )
            }

            LoadResult.Page(
                data = response,
                prevKey = if (nextPageNumber == 0) null else nextPageNumber - 1,
                nextKey = if (response.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}