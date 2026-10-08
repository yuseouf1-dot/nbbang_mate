package com.nbbang.matching.exception

/**
 * [기본 주석] 백엔드에서 에러가 발생했을 때 프론트엔드로 전달할 규격화된 에러 응답 객체(DTO)입니다.
 */
data class ErrorResponse(
    val status: Int, // HTTP 상태 코드 (예: 400, 500)
    val message: String // 클라이언트에게 보여줄 친절한 에러 메시지
)