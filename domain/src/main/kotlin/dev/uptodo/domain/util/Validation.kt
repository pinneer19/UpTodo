package dev.uptodo.domain.util

internal fun String.isEmail(): Boolean {
    val regex = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+\$")
    return regex.matches(this)
}

/*  Password rules (at least):
    8 characters length
    2 letters in uppercase
    1 special character
    2 numerals (0-9)
    3 letters in lowercase
*/

internal fun String.containsTwoUpperCase(): Boolean {
    val regex = Regex(".*[A-Z].*[A-Z]")
    return regex.containsMatchIn(this)
}

internal fun String.containsSpecialCharacter(): Boolean {
    val regex = Regex(".*[^a-zA-z\\d]")
    return regex.containsMatchIn(this)
}

internal fun String.containsTwoNumerals(): Boolean {
    val regex = Regex(".*\\d.*\\d")
    return regex.containsMatchIn(this)
}

internal fun String.containsThreeLowerCase(): Boolean {
    val regex = Regex(".*[a-z].*[a-z].*[a-z]")
    return regex.containsMatchIn(this)
}