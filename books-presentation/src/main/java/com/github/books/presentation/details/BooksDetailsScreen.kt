package com.github.books.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun BooksDetailsScreen(
    modifier: Modifier = Modifier,
    volumeInfoId: Long,
    viewModel: BooksDetailsViewModel
) {
    val volumeInfo by viewModel.volumeInfo.collectAsState()
    LaunchedEffect(key1 = volumeInfoId) {
        viewModel.volumeInfo(volumeInfoId)
    }
    Column(modifier) {
        Text(text = volumeInfo.title)
        Text(text = volumeInfo.description)
    }
}
