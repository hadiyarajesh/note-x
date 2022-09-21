package com.hadiyarajesh.notex.utility

import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

     fun LocalDateTime.getTimeDifference(): Long {
        val now = LocalDateTime.now()
        val duration = Duration.between(now, this).toMillis()
        return if (duration > 0) duration else 0
    }

    fun ChronoUnit.getTimeDifference(): Long {
        val now = LocalDateTime.now()
        val addedduration = now.plus(1, this)
        val duration = Duration.between(now, addedduration).toMillis()
        return if (duration > 0) duration else 0
    }
