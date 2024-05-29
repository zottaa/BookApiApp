package com.github.books.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.books.domain.models.Volume
import com.github.books.presentation.R

@Composable
fun BooksListScreen(
    modifier: Modifier = Modifier,
    viewModel: BooksListViewModel,
    onVolumeClick: (Long) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val query by viewModel.query.collectAsState()

    Column(modifier) {
        TextField(value = query, onValueChange = { newQuery ->
            if (newQuery != query) {
                viewModel.volumes(newQuery)
            }
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            placeholder = { Text(text = stringResource(R.string.enter_query)) })
        Spacer(modifier = Modifier.size(6.dp))
        VolumesMainContent(state, onVolumeClick)
    }
}

@Composable
internal fun VolumesMainContent(
    state: UiState<List<Volume>, UiError>,
    onVolumeClick: (Long) -> Unit
) {
    when (state) {
        is UiState.Error -> ErrorMessage(state, onVolumeClick)
        UiState.Initial -> Unit
        is UiState.Loading -> LoadingIndicator(state, onVolumeClick)
        is UiState.Success -> VolumesList(state, onVolumeClick)
    }
}

@Composable
internal fun ErrorMessage(
    state: UiState.Error<List<Volume>, UiError>,
    onVolumeClick: (Long) -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.error)
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = state.error.message(context),
            color = MaterialTheme.colorScheme.onError
        )
    }
    if (state.data != null) {
        VolumesList(state.data, onVolumeClick)
    }
}

@Composable
internal fun LoadingIndicator(
    state: UiState.Loading<List<Volume>, UiError>,
    onVolumeClick: (Long) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
    if (state.data != null) {
        VolumesList(state.data, onVolumeClick)
    }
}

