package com.example.gittest

import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.lang.Exception

/**
 *
 * 请描述类的作用
 * @author linxiao date: 2022年4月12日, 0012 14:45
 */
class HomePageDataSource(val service: IMainService) : PagingSource<Int, Data>() {
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val currentLoadingPageKey = params.key ?: 1
            val listData = service.getListData(currentLoadingPageKey, 20)
            val responseData = mutableListOf<Data>()
            val data = listData.myData
            responseData.addAll(data)
            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
            val nextKey =
                if (currentLoadingPageKey >= listData.totalPages) null else currentLoadingPageKey.plus(
                    1
                )
            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}