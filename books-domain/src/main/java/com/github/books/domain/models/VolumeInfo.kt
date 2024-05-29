package com.github.books.domain.models

data class VolumeInfo(
    val id: Long,
    val title: String,
    val authors: List<String>,
    val publisher: String,
    val publishedDate: String,
    val description: String,
    val pageCount: Int,
    val imageLinks: ImageLinks,
    val language: String,
    val previewLink: String,
    val infoLink: String
)
