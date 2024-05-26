package com.github.booksapi.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VolumeCloud(
    @SerialName("kind")
    val kind: String,
    @SerialName("id")
    val id: String,
    @SerialName("selfLink")
    val selfLink: String,
    @SerialName("volumeInfo")
    val volumeInfo: VolumeInfoCloud
)
