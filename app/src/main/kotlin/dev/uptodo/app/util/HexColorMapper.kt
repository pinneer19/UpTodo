package dev.uptodo.app.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun Color.Companion.fromHex(colorString: String) =
    Color(android.graphics.Color.parseColor(colorString))

@OptIn(ExperimentalStdlibApi::class)
fun Color?.toHexString(): String {
    val hexString = this?.toArgb()?.toHexString()
    return "#$hexString"
}