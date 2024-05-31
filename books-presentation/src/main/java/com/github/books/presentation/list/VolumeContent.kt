package com.github.books.presentation.list

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.books.domain.models.Volume
import com.github.books.presentation.R

@Composable
internal fun VolumeContent(volume: Volume, onVolumeClick: (Long) -> Unit) {
    Row(modifier = Modifier
        .padding(bottom = 6.dp)
        .fillMaxWidth()
        .clickable {
            onVolumeClick(volume.volumeInfo.id)
        }) {
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
