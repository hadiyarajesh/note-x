package com.hadiyarajesh.notex.utility

import com.hadiyarajesh.notex.database.model.RepetitionStrategy
import java.time.temporal.ChronoUnit

fun RepetitionStrategy.getDurationUnitfromStrategy() = when (this) {
    RepetitionStrategy.Daily -> {
        ChronoUnit.DAYS
    }
    RepetitionStrategy.Monthly -> {
        ChronoUnit.MONTHS
    }
    RepetitionStrategy.Weekly -> {
        ChronoUnit.WEEKS
    }
    RepetitionStrategy.Yearly -> {
        ChronoUnit.YEARS
    }
    else -> {
        ChronoUnit.SECONDS
    }
}