package com.github.books.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.github.books.domain.models.Volume

@Composable
fun BooksMain() {
    BooksMain(viewModel())
}

@Composable
internal fun BooksMain(viewModel: BooksMainViewModel) {
    val state by viewModel.state.collectAsState()
    val query by viewModel.query.collectAsState()

    Column {
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
        if (state != UiState.Initial) {
            BooksMainContent(state)
        }
    }
}

@Composable
fun BooksMainContent(state: UiState<List<Volume>?, UiError>) {
    Column {
        if (state is UiState.Error) {
            ErrorMessage(state.error)
        } else if (state is UiState.Loading) {
            ProgressIndicator()
        }
        if (state.data != null) {
            Volumes(state.data)
        }
    }
}

@Composable
fun ErrorMessage(error: UiError) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.error)
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = error.message(context),
            color = MaterialTheme.colorScheme.onError
        )
    }
}

@Composable
fun ProgressIndicator() {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun Volumes(volumes: List<Volume>) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(volumes) { volume ->
            Volume(volume)
        }
    }
}

@Composable
fun Volume(volume: Volume) {
    println(volume.volumeInfo.imageLinks.thumbnail)
    Row(modifier = Modifier.padding(bottom = 6.dp)) {
        var imageVisible by remember { mutableStateOf(true) }
        if (imageVisible) {
            AsyncImage(
                model =
                volume.volumeInfo.imageLinks.smallThumbnail.ifBlank {
                    volume.volumeInfo.imageLinks.thumbnail
                },
                onError = {
                    imageVisible = false
                },
                contentDescription = stringResource(R.string.content_description_thumbnail_of_book),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Column(
            Modifier.padding(6.dp)
        ) {
            Text(text = volume.volumeInfo.title)
            Spacer(modifier = Modifier.size(2.dp))
            Text(text = volume.volumeInfo.authors.joinToString())
            Spacer(modifier = Modifier.size(2.dp))
            Text(text = volume.volumeInfo.language)
            Spacer(modifier = Modifier.size(2.dp))
            Text(text = "${volume.volumeInfo.publishedDate} ${volume.volumeInfo.publisher}")
        }
    }
}
