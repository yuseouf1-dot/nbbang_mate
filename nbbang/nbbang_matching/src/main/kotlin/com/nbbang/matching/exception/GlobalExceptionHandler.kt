package com.nbbang.matching.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * [기본 주석] 프로젝트 전역(모든 컨트롤러)에서 발생하는 예외(에러)를 가로채어
 * 일관된 형태(ErrorResponse JSON)로 프론트엔드에 반환하는 역할을 수행합니다.
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    /**
     * [기능 주석] 비즈니스 로직(예: Matching.kt에서 인원이 꽉 찼을 때 등)에서 발생시킨
     * IllegalStateException을 낚아채서 처리합니다.
     */
    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(e: IllegalStateException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(), // 400 Bad Request 상태 코드 반환
            message = e.message ?: "잘못된 요청입니다." // 도메인 코드에 작성해둔 구체적인 메시지 전달
        )
        // 예쁘게 포장된 JSON 응답을 클라이언트로 반환
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
}