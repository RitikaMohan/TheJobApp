package com.example.thejobapp.domain.repository

import android.content.Context
import com.example.thejobapp.data.database.AppDatabase
import com.example.thejobapp.domain.model.BookmarkEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookmarkRepository(context: Context) {
    private val bookmarkDao = AppDatabase.getDatabase(context).bookmarkDao()

    suspend fun addBookmark(bookmark: BookmarkEntity) = withContext(Dispatchers.IO) {
        bookmarkDao.insertBookmark(bookmark)
    }

    suspend fun removeBookmark(bookmark: BookmarkEntity) = withContext(Dispatchers.IO) {
        bookmarkDao.deleteBookmark(bookmark)
    }

    suspend fun getAllBookmarks(): List<BookmarkEntity> = withContext(Dispatchers.IO) {
        bookmarkDao.getAllBookmarks()
    }

    suspend fun isBookmarked(id: String): Boolean = withContext(Dispatchers.IO) {
        bookmarkDao.getBookmarkById(id) != null
    }
}