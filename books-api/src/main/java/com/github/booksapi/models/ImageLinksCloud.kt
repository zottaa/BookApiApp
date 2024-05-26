package com.github.booksapi.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageLinksCloud(
    @SerialName("smallThumbnail")
    val smallThumbnail: String?,
    @SerialName("thumbnail")
    val thumbnail: String?
)
