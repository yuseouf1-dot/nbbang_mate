package com.nbbang.matching.controller

import com.nbbang.matching.domain.ChatMessage
import com.nbbang.matching.repository.ChatMessageRepository
import org.springframework.web.bind.annotation.*

/**
 * [기본 주석] 채팅 메시지 요청 바구니(DTO)
 */
data class ChatRequest(val senderId: Long, val senderNickname: String, val content: String)

/**
 * [기본 주석] 매칭 방 내부의 실시간 채팅 기능을 처리하는 REST 컨트롤러입니다.
 */
@RestController
@RequestMapping("/api/matchings/{matchingId}/chat")
class ChatController(private val chatMessageRepository: ChatMessageRepository) {

    /**
     * [기능 주석] 특정 매칭 방(matchingId)의 모든 채팅 내역을 보낸 시간순(과거->최신)으로 조회합니다.
     * 프론트엔드에서 폴링(Polling) 방식으로 이 API를 주기적으로 호출하여 새 메시지를 갱신합니다.
     */
    @GetMapping
    fun getChats(@PathVariable matchingId: Long): List<ChatMessage> {
        return chatMessageRepository.findByMatchingIdOrderBySentAtAsc(matchingId)
    }

    /**
     * [기능 주석] 특정 매칭 방에 새로운 채팅 메시지를 전송하고 DB에 저장합니다.
     */
    @PostMapping
    fun sendChat(@PathVariable matchingId: Long, @RequestBody request: ChatRequest): ChatMessage {
        val chat = ChatMessage(
            matchingId = matchingId,
            senderId = request.senderId,
            senderNickname = request.senderNickname,
            content = request.content
        )
        // 저장된 채팅 객체를 반환하여 클라이언트에서 즉시 렌더링할 수 있도록 지원
        return chatMessageRepository.save(chat)
    }
}