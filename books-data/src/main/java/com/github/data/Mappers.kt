package com.github.data

import com.github.books.database.models.ImageLinksCache
import com.github.books.database.models.VolumeCache
import com.github.books.database.models.VolumeInfoCache
import com.github.books.domain.models.ImageLinks
import com.github.books.domain.models.Volume
import com.github.books.domain.models.VolumeInfo
import com.github.booksapi.models.ImageLinksCloud
import com.github.booksapi.models.VolumeCloud
import com.github.booksapi.models.VolumeInfoCloud

internal fun VolumeCache.toVolume(): Volume =
    with(this) {
        Volume(
            id = id,
            volumeInfo = volumeInfo.toVolumeInfo()
        )
    }


internal fun VolumeInfoCache.toVolumeInfo(): VolumeInfo =
    with(this) {
        VolumeInfo(
            id = id,
            title = title,
            authors = authors,
            publisher = publisher,
            publishedDate = publishedDate,
            description = description,
            pageCount = pageCount,
            imageLinks = imageLinks.toImageLinks(),
            language = language,
            previewLink = previewLink,
            infoLink = infoLink
        )
    }


internal fun ImageLinksCache.toImageLinks(): ImageLinks =
    with(this) {
        ImageLinks(
            smallThumbnail = smallThumbnail,
            thumbnail = thumbnail
        )
    }

internal fun VolumeCloud.toVolume(volumeInfoId: Long): Volume =
    with(this) {
        Volume(
            id = id,
            volumeInfo = volumeInfo.toVolumeInfo(volumeInfoId)
        )
    }


internal fun VolumeInfoCloud.toVolumeInfo(id: Long): VolumeInfo =
    with(this) {
        VolumeInfo(
            id = id,
            title = title ?: "",
            authors = authors ?: listOf(),
            publisher = publisher ?: "",
            publishedDate = publishedDate ?: "",
            description = description ?: "",
            pageCount = pageCount ?: 0,
            imageLinks = imageLinks?.toImageLinks() ?: ImageLinks(),
            language = language ?: "",
            previewLink = previewLink ?: "",
            infoLink = infoLink ?: ""
        )
    }


internal fun ImageLinksCloud.toImageLinks(): ImageLinks {
    return with(this) {
        ImageLinks(
            smallThumbnail = smallThumbnail?.let(::ensureHttps) ?: "",
            thumbnail = thumbnail?.let(::ensureHttps) ?: ""
        )
    }
}

private fun ensureHttps(url: String): String =
    if (url.contains("http:")) {
        url.replaceFirst("http", "https")
    } else
        url


internal fun Volume.toVolumeCache(): VolumeCache =
    with(this) {
        VolumeCache(
            id = id,
            volumeInfo = volumeInfo.toVolumeInfoCache()
        )
    }

internal fun VolumeInfo.toVolumeInfoCache(): VolumeInfoCache =
    with(this) {
        VolumeInfoCache(
            id = id,
            title = title,
            authors = authors,
            publisher = publisher,
            publishedDate = publishedDate,
            description = description,
            pageCount = pageCount,
            imageLinks = imageLinks.toImageLinksCache(),
            language = language,
            previewLink = previewLink,
            infoLink = infoLink
        )
    }


internal fun ImageLinks.toImageLinksCache(): ImageLinksCache =
    with(this) {
        ImageLinksCache(
            id = System.currentTimeMillis(),
            smallThumbnail = smallThumbnail,
            thumbnail = thumbnail
        )
    }

