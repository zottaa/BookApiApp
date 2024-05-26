package com.github.booksapi.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VolumeResponse(
    @SerialName("kind")
    val kind: String,
    @SerialName("totalTime")
    val totalTime: Int,
    @SerialName("items")
    val items: List<VolumeCloud>
)
