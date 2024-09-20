package dev.uptodo.app.ui.util

import androidx.compose.ui.graphics.Color
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

fun Color.darken(factor: Float): Color {

    val hsv = FloatArray(3)
    val r = this.red
    val g = this.green
    val b = this.blue

    val max = max(r, max(g, b))
    val min = min(r, min(g, b))
    val delta = max - min

    val h = when {
        delta == 0f -> 0f
        max == r -> (60 * ((g - b) / delta) + 360) % 360
        max == g -> (60 * ((b - r) / delta) + 120) % 360
        else -> (60 * ((r - g) / delta) + 240) % 360
    }

    val s = if (max == 0f) 0f else delta / max
    val v = max

    hsv[0] = h
    hsv[1] = s
    hsv[2] = v

    hsv[2] = (hsv[2] * factor).coerceIn(0f, 1f)

    val c = hsv[2] * hsv[1]
    val x = c * (1 - ((hsv[0] / 60) % 2 - 1).absoluteValue)
    val m = hsv[2] - c

    val (rNew, gNew, bNew) = when {
        hsv[0] in 0f..60f -> Triple(c, x, 0f)
        hsv[0] in 60f..120f -> Triple(x, c, 0f)
        hsv[0] in 120f..180f -> Triple(0f, c, x)
        hsv[0] in 180f..240f -> Triple(0f, x, c)
        hsv[0] in 240f..300f -> Triple(x, 0f, c)
        else -> Triple(c, 0f, x)
    }

    return Color((rNew + m), (gNew + m), (bNew + m), this.alpha)
}