package com.payback.pixabay.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.payback.pixabay.response.Hit
import okio.IOException
import retrofit2.HttpException

const val PIXABAY_STARTING_PAGE_INDEX = 1

class PixabayPagingSource(
    private val pixabayApi: PixabayImageApiService,
    private val query: String
) :PagingSource<Int, Hit>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hit> {
        val position = params.key ?: PIXABAY_STARTING_PAGE_INDEX

        return try {
            val response = pixabayApi.searchPhotos(query, position, params.loadSize)
            val photos = response.hits
            LoadResult.Page(
                data = photos,
                prevKey = if(position == PIXABAY_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if(photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException){
            LoadResult.Error(exception)
        } catch (exception: HttpException){
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Hit>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}