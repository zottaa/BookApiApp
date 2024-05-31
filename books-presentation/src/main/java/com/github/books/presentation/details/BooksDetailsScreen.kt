package com.github.books.presentation.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.books.presentation.R

@Composable
fun BooksDetailsScreen(
    modifier: Modifier = Modifier,
    volumeInfoId: Long,
    viewModel: BooksDetailsViewModel
) {
    val volumeInfo by viewModel.volumeInfo.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = volumeInfoId) {
        viewModel.volumeInfo(volumeInfoId)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = volumeInfo.imageLinks.thumbnail,
            contentDescription = volumeInfo.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = volumeInfo.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = stringResource(R.string.by, volumeInfo.authors.joinToString(", ")),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = stringResource(
                R.string.published_by_on,
                volumeInfo.publisher,
                volumeInfo.publishedDate
            ),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = stringResource(R.string.pages, volumeInfo.pageCount),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = volumeInfo.description,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.language, volumeInfo.language),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                followTheLink(context, volumeInfo.previewLink)
            }) {
                Text(text = stringResource(R.string.preview))
            }
            Button(onClick = {
                followTheLink(context, volumeInfo.infoLink)
            }) {
                Text(text = stringResource(R.string.more_info))
            }
        }
    }
}

private fun followTheLink(context: Context, url: String) {
    Intent(Intent.ACTION_VIEW, Uri.parse(url)).also {
        context.startActivity(it)
    }
}
