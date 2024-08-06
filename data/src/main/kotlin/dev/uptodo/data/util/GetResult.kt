package dev.uptodo.data.util

internal suspend fun <T> getResult(action: suspend () -> T): Result<T> {
    return try {
        val result: T = action()
        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }
}