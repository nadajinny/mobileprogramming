package com.mb.traveltogether.model

import com.google.firebase.Timestamp

data class Trip(
    val title: String = "",
    val creatorId: String = "",
    val isPublic: Boolean = false,
    val members: List<String> = listOf(),
    val createdAt: Timestamp?= null
)
