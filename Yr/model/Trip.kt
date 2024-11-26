package com.mb.traveltogether.model

import com.google.firebase.Timestamp

data class Trip(
    val title: String = "", //여행 제목
    val creatorId: String = "", //만든사람 ID
    val isPublic: Boolean = false, //공개 여부 기본값 false
    val members: List<String> = listOf(), //참여 멤버 ID list
    val createdAt: Timestamp?= null //만든 시간
)
