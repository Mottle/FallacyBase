package dev.deepslate.fallacy.utils

const val TICKS_PER_SECOND = 20

const val TICKS_PER_MINUTE = 60 * TICKS_PER_SECOND

const val TICKS_PER_HOUR = 60 * TICKS_PER_MINUTE

const val TICKS_PER_DAY = 24 * TICKS_PER_HOUR

fun seconds2Ticks(seconds: Int): Int = seconds * 20

fun minutes2Ticks(minutes: Int): Int = 60 * seconds2Ticks(minutes)

fun hours2Ticks(hours: Int): Int = 60 * minutes2Ticks(hours)

fun days2Ticks(days: Int): Int = 24 * hours2Ticks(days)