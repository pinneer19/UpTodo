package dev.uptodo.app.util.extension

fun String.getSubstringBeforeRouteSymbols(): String {
    val regex = Regex("([^?/]+)")
    return regex.find(this)?.value ?: this
}