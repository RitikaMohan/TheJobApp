package com.example.thejobapp.presentation.bookmark

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thejobapp.domain.model.BookmarkEntity
import com.example.thejobapp.domain.repository.BookmarkRepository
import kotlinx.coroutines.launch

class BookmarkViewModel(context: Context) : ViewModel() {
    private val repository = BookmarkRepository(context)

    private val _bookmarks = MutableLiveData<List<BookmarkEntity>>()
    val bookmarks: LiveData<List<BookmarkEntity>> get() = _bookmarks

    fun fetchBookmarks() {
        viewModelScope.launch {
            val data = repository.getAllBookmarks()
            _bookmarks.postValue(data)
        }
    }

    fun addBookmark(bookmark: BookmarkEntity) {
        viewModelScope.launch {
            repository.addBookmark(bookmark)
            fetchBookmarks() // Refresh the list
        }
    }

    fun removeBookmark(bookmark: BookmarkEntity) {
        viewModelScope.launch {
            repository.removeBookmark(bookmark)
            fetchBookmarks() // Refresh the list
        }
    }

    suspend fun isBookmarked(id: String): Boolean {
        return repository.isBookmarked(id)
    }
}