package com.example.flightmate.domain.exception

sealed class AppException(
    message: String? = null,
    throwable: Throwable? = null
): Exception(message, throwable) {

    /** 因 HTTP 層錯誤發生的例外。 */
    class HttpError(val code: Int, val errorBody: String?): AppException()

    /** 因無法連線、或 timeout 等原因發生的例外。 */
    data object NetworkError : AppException("Network error") {
        private fun readResolve(): Any = NetworkError
    }

    /** 因 API 狀態雖為成功，但內容為失敗發生的例外。 */
    class ApiError(override val message: String) : AppException(message)

    /** 未知錯誤。 */
    class UnknownError(cause: Throwable?) : AppException("Unknown error", cause)
}
