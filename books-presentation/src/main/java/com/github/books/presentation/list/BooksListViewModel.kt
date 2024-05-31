package com.github.books.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.github.books.domain.BooksRepository
import com.github.books.domain.models.Volume
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksListViewModel @Inject constructor(
    private val booksRepository: BooksRepository.All
) : ViewModel() {
    val volumes: StateFlow<PagingData<Volume>>
        get() = _volumes
    private val _volumes: MutableStateFlow<PagingData<Volume>> = MutableStateFlow(PagingData.empty())


    val query: StateFlow<String>
        get() = _query
    private val _query: MutableStateFlow<String> = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val searchQuery = _query
        .debounce(SEARCH_DELAY)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            booksRepository.volumes(query)
        }


    init {
        viewModelScope.launch {
            searchQuery.collect { pagingData ->
                _volumes.value = pagingData
            }
        }
    }

    fun volumes(query: String) {
        _query.value = query
    }

    companion object {
        private const val SEARCH_DELAY = 1000L
    }
}
