package com.github.booksapi.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VolumeInfoCloud(
    @SerialName("title")
    val title: String?,
    @SerialName("authors")
    val authors: List<String>?,
    @SerialName("publisher")
    val publisher: String?,
    @SerialName("publishedDate")
    val publishedDate: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("pageCount")
    val pageCount: Int?,
    @SerialName("imageLinks")
    val imageLinks: ImageLinksCloud?,
    @SerialName("language")
    val language: String?,
    @SerialName("previewLink")
    val previewLink: String?,
    @SerialName("infoLink")
    val infoLink: String?
)
