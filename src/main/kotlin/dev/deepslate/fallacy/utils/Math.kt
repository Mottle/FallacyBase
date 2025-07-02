package dev.deepslate.fallacy.utils

import kotlin.math.exp

fun sigmoid(x: Double): Double {
    val nExp = exp(-x)
    return 1.0 / (1.0 + nExp)
}

fun sigmoid(x: Float): Float {
    val nExp = exp(-x)
    return 1f / (1f + nExp)
}


fun nigmoid(x: Double): Double = 2.0 * (sigmoid(x) - 0.5)

fun nigmoid(x: Float): Float = 2f * (sigmoid(x) - 0.5f)