package com.github.books.presentation.list

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.github.books.domain.models.Volume
import com.github.books.presentation.R

@Composable
fun BooksListScreen(
    modifier: Modifier = Modifier,
    viewModel: BooksListViewModel,
    onVolumeClick: (Long) -> Unit
) {
    val volumes = viewModel.volumes.collectAsLazyPagingItems()
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
        VolumesMainContent(volumes, onVolumeClick)
    }
}

@Composable
internal fun VolumesMainContent(
    volumes: LazyPagingItems<Volume>,
    onVolumeClick: (Long) -> Unit
) {
    val context = LocalContext.current
    val currentVolumesState = volumes.loadState.refresh

    LaunchedEffect(key1 = currentVolumesState) {
        if (currentVolumesState is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + currentVolumesState.error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (currentVolumesState is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    count = volumes.itemCount,
                    key = volumes.itemKey { it.volumeInfo.id },
                ) { index ->
                    volumes[index]?.let { volume ->
                        VolumeContent(
                            volume = volume,
                            onVolumeClick = onVolumeClick
                        )
                    }
                }
                item {
                    if (volumes.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

