package com.github.books.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import javax.inject.Provider

@HiltViewModel
class BooksMainViewModel @Inject constructor(
    searchByQueryUseCase: Provider<SearchByQueryUseCase>
) : ViewModel() {
    val state: StateFlow<UiState<List<Volume>?, UiError>>
        get() = _state
    private val _state: MutableStateFlow<UiState<List<Volume>?, UiError>> =
        MutableStateFlow(UiState.Initial)

    val query: StateFlow<String>
        get() = _query
    private val _query: MutableStateFlow<String> = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val searchQuery = _query
        .debounce(SEARCH_DELAY)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            searchByQueryUseCase.get().invoke(query)
        }


    init {
        viewModelScope.launch {
            searchQuery.collect {
                _state.value = it
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
