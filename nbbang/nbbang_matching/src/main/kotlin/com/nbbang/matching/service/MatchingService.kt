package com.nbbang.matching.service

import com.nbbang.matching.domain.Matching
import com.nbbang.matching.dto.*
import com.nbbang.matching.repository.MatchingRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * [기본 주석] 밥약 매칭 방과 관련된 핵심 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
class MatchingService(
    private val matchingRepository: MatchingRepository,
    private val settlementService: SettlementService // [기능 주석] N빵 정산을 위해 SettlementService를 의존성 주입(DI) 받습니다.
) {

    /**
     * [기능 주석] 새로운 밥약 매칭 방을 개설합니다.
     */
    @Transactional
    fun createMatching(request: MatchingCreateRequest): Long {
        val matching = Matching(
            title = request.title,
            restaurantName = request.restaurantName,
            maxParticipants = request.maxParticipants,
            deadline = request.deadline,
            createdByUserId = request.createdByUserId,
            // 배달(DELIVERY)일 때만 배달비를 저장하고, 방문(DINE_IN)이면 0원으로 강제 초기화합니다.
            deliveryFee = if (request.orderType == "DINE_IN") 0 else request.deliveryFee,
            orderType = request.orderType,
            // 배달일 때만 만날 장소를 저장합니다.
            deliveryLocation = if (request.orderType == "DELIVERY") request.deliveryLocation else null
        )

        // 🚀 핵심: 방을 개설한 방장도 '참여자 명부'의 첫 번째(1번)로 자동 등록시킵니다.
        matching.join(request.createdByUserId, request.creatorNickname, request.creatorOrderAmount)

        return matchingRepository.save(matching).id!!
    }

    /**
     * [기능 주석] 특정 매칭 방을 삭제합니다.
     */
    @Transactional
    fun deleteMatching(id: Long, requestedUserId: Long): String {
        val matching = matchingRepository.findById(id)
            .orElseThrow { IllegalArgumentException("해당 매칭글이 존재하지 않습니다.") }

        // 삭제를 요청한 사용자가 방장인지 검증하는 보안 로직입니다.
        if (matching.createdByUserId != requestedUserId) {
            throw IllegalStateException("방장만 이 매칭 방을 삭제할 수 있습니다.")
        }

        matchingRepository.delete(matching)
        return "${id}번 매칭글이 성공적으로 삭제되었습니다."
    }

    /**
     * [기능 주석] 개설된 모든 매칭 방의 목록을 조회합니다.
     */
    fun getAllMatchings(): List<Matching> {
        return matchingRepository.findAll()
    }

    /**
     * [기능 주석] 사용자가 특정 매칭 방에 참여할 때 호출되며, 인원 추가 및 N빵 정산을 수행합니다.
     */
    @Transactional
    fun joinMatching(matchingId: Long, request: JoinRequest): Map<String, Any> {
        val matching = matchingRepository.findById(matchingId)
            .orElseThrow { IllegalArgumentException("해당 매칭글을 찾을 수 없습니다.") }

        // 🚀 중복 검사 및 참여자 명부에 유저 정보를 기록합니다. (마감 시 자동 CLOSED 처리 포함)
        matching.join(request.userId, request.nickname, request.myOrderAmount)

        // 정산 서비스를 호출하여 현재까지의 참여 인원을 기준으로 1/N 배달비를 계산해 반환합니다.
        return settlementService.calculateSettlement(
            deliveryFee = matching.deliveryFee,
            currentParticipants = matching.currentParticipants,
            myOrderAmount = request.myOrderAmount
        )
    }

    /**
     * [기능 주석] 특정 매칭 방의 상세 정보를 조회합니다.
     */
    fun getMatchingById(matchingId: Long): Matching {
        return matchingRepository.findById(matchingId)
            .orElseThrow { IllegalArgumentException("해당 매칭글을 찾을 수 없습니다.") }
    }

    /**
     * [기능 주석] 특정 매칭 방의 정보를 수정합니다.
     */
    @Transactional
    fun updateMatching(id: Long, requestedUserId: Long, request: MatchingUpdateRequest): String {
        val matching = matchingRepository.findById(id)
            .orElseThrow { IllegalArgumentException("해당 매칭글이 존재하지 않습니다.") }

        // 수정을 요청한 사용자가 방장인지 검증하는 보안 로직입니다.
        if (matching.createdByUserId != requestedUserId) {
            throw IllegalStateException("방장만 이 매칭 방을 수정할 수 있습니다.")
        }

        matching.updateRoomInfo(
            title = request.title,
            restaurantName = request.restaurantName,
            maxParticipants = request.maxParticipants,
            deadline = request.deadline
        )

        return "${id}번 매칭글이 성공적으로 수정되었습니다."
    }
}