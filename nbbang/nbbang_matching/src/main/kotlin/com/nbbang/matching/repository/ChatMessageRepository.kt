package com.nbbang.matching.repository

import com.nbbang.matching.domain.ChatMessage
import org.springframework.data.jpa.repository.JpaRepository

/**
 * [기본 주석] ChatMessage 엔티티를 데이터베이스에 저장하고 조회하는 JPA 리포지토리 인터페이스입니다.
 */
interface ChatMessageRepository : JpaRepository<ChatMessage, Long> {

    /**
     * [기능 주석] 특정 매칭 방(matchingId)에 속한 모든 채팅 내역을
     * 보낸 시간(sentAt) 기준 오름차순(과거->최신)으로 조회합니다.
     */
    fun findByMatchingIdOrderBySentAtAsc(matchingId: Long): List<ChatMessage>
}