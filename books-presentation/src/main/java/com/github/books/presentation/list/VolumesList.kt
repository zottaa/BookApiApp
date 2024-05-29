package com.github.books.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.unit.dp
import com.github.books.domain.models.Volume

@Composable
internal fun VolumesList(
    state: UiState.Success<List<Volume>, UiError>,
    onVolumeClick: (Long) -> Unit
) {
    VolumesList(volumes = state.data, onVolumeClick = onVolumeClick)
}

@Composable
internal fun VolumesList(
    volumes: List<Volume>,
    onVolumeClick: (Long) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(volumes) { volume ->
            key(volume.id) {
                VolumeContent(volume, onVolumeClick)
            }
        }
    }
}
