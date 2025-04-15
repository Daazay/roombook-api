package core.utils

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T): Result<T>(data, null)
    class Failure<T>(message: String): Result<T>(null, message)
}