package com.nbbang.matching.dto
import java.time.LocalDateTime

/**
 * [기본 주석] 새로운 밥약 매칭 방을 생성할 때 클라이언트로부터 전달받는 데이터 전송 객체(DTO)입니다.
 */
data class MatchingCreateRequest(
    val title: String,
    val restaurantName: String,
    val maxParticipants: Int,
    val deadline: LocalDateTime,
    val createdByUserId: Long,
    val creatorNickname: String, // [기능 주석] 방장의 닉네임을 명부에 등록하기 위해 전달받습니다.
    val creatorOrderAmount: Int, // [기능 주석] 방장의 초기 주문 금액입니다.
    val deliveryFee: Int,
    val orderType: String, // 배달(DELIVERY)인지 방문(DINE_IN)인지 여부
    val deliveryLocation: String? // 배달일 경우 만날 장소 정보
)

/**
 * [기본 주석] 매칭 방 정보를 수정할 때 사용하는 DTO입니다.
 */
data class MatchingUpdateRequest(
    val title: String,
    val restaurantName: String,
    val maxParticipants: Int,
    val deadline: LocalDateTime
)

/**
 * [기본 주석] 생성된 매칭 방에 새로운 사용자가 참여할 때 사용하는 DTO입니다.
 */
data class JoinRequest(
    val userId: Long,     // [기능 주석] 중복 참여 검증을 위해 사용자의 고유 ID를 받습니다.
    val nickname: String, // 명부에 표시할 참여자의 닉네임입니다.
    val myOrderAmount: Int // N빵 정산을 위해 본인이 시킬 메뉴의 총 금액입니다.
)