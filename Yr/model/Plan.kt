package com.mb.traveltogether.model

import com.google.firebase.Timestamp

data class Plan(
    val planId: String = "",//일정 ID
    val time: Timestamp ?= null,//일정 시간
    val location: String = "",//장소
    val description: String = "",//설명
    val lastUpdateBy: String = "",//마지막 수정한 사용자 ID
    val lastUpdateAt: Timestamp ?= null//마지막 수정 시간
)
