package com.shakiv.whatsappsample.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shakiv.whatsappsample.data.database.MessageDao
import com.shakiv.whatsappsample.data.model.Message
import kotlinx.coroutines.delay

class MessagePagingSource(
    private val messageDao: MessageDao,
    private val userId: Long
) :
    PagingSource<Int, Message>() {
    override fun getRefreshKey(state: PagingState<Int, Message>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Message> {

        val page = params.key ?: 0

        return try {

            val users = messageDao.getAllMessages(userId, params.loadSize, page * params.loadSize)

            if (page != 0) delay(1000)

            LoadResult.Page(
                users,
                prevKey = if (page == 0) null else page.minus(1),
                nextKey = if (users.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}