package com.nbbang.matching.domain

import jakarta.persistence.*
import java.time.LocalDateTime

/**
 * [기본 주석] 매칭 방 내부의 실시간 채팅 메시지 정보를 데이터베이스에 저장하는 엔티티 클래스입니다.
 */
@Entity
@Table(name = "chat_messages")
class ChatMessage(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,

    // [기능 주석] 어떤 매칭 방에서 전송된 메시지인지 구분하는 식별자입니다.
    @Column(nullable = false) val matchingId: Long,

    @Column(nullable = false) val senderId: Long,
    @Column(nullable = false) val senderNickname: String,
    @Column(nullable = false) val content: String,

    // [기능 주석] 메시지가 전송된 시간을 기록하여, 프론트엔드에서 시간순으로 정렬할 수 있게 합니다.
    @Column(nullable = false) val sentAt: LocalDateTime = LocalDateTime.now()
)