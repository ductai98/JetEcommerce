package com.taild.domain.exception

sealed class BusinessException(message: String) : Exception(message) {
    class IOException(message: String) : BusinessException(message)
    class HttpException(message: String) : BusinessException(message)
}