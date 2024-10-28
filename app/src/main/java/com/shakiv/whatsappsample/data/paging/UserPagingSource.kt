package com.shakiv.whatsappsample.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shakiv.whatsappsample.data.database.UserDao
import com.shakiv.whatsappsample.data.model.User
import kotlinx.coroutines.delay

class UserPagingSource(
    private val userDao: UserDao,
    private val query: String
) : PagingSource<Int, User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: 0

        return try {
            val users =
                if (query.isEmpty()) {
                    userDao.getAllUser(params.loadSize, page * params.loadSize)
                } else {
                    userDao.searchUsers(searchQuery = query)
                }


            if (page != 0) delay(2000)

            LoadResult.Page(
                users,
                prevKey = if (page == 0) null else page.minus(1),
                nextKey = if (users.isEmpty() || query.isNotEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}