package com.github.books.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.books.domain.BooksRepository
import com.github.books.domain.models.ImageLinks
import com.github.books.domain.models.VolumeInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksDetailsViewModel @Inject constructor(
    private val repository: BooksRepository.Details
) : ViewModel() {

    val volumeInfo: StateFlow<VolumeInfo>
        get() = _volumeInfo
    private val _volumeInfo: MutableStateFlow<VolumeInfo> = MutableStateFlow(
        VolumeInfo(
            0, "", listOf(), "", "", "", 0, ImageLinks("", ""), "", "", ""
        )
    )


    fun volumeInfo(volumeInfoId: Long) {
        viewModelScope.launch {
            val volumeInfo = repository.volumeInfo(volumeInfoId)
            _volumeInfo.value = volumeInfo
        }
    }
}
