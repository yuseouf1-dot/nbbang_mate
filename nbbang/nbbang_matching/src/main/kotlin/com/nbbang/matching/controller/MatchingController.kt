package com.nbbang.matching.controller

import com.nbbang.matching.domain.Matching
import com.nbbang.matching.dto.MatchingCreateRequest
import com.nbbang.matching.dto.MatchingUpdateRequest
import com.nbbang.matching.dto.JoinRequest
import com.nbbang.matching.service.MatchingService
import org.springframework.web.bind.annotation.*

/**
 * [기본 주석] 밥약 매칭 방의 생성, 조회, 수정, 삭제 및 참여(N빵 정산)를 관리하는 REST 컨트롤러입니다.
 */
@RestController
@RequestMapping("/api/matchings")
class MatchingController(private val matchingService: MatchingService) {

    /**
     * [기능 주석] 새로운 밥약 매칭 방을 개설합니다.
     */
    @PostMapping
    fun createMatching(@RequestBody request: MatchingCreateRequest): String {
        val id = matchingService.createMatching(request)
        return "매칭글이 생성되었습니다. (ID: $id)"
    }

    /**
     * [기능 주석] 개설된 모든 매칭 방 목록을 조회하여 반환합니다.
     */
    @GetMapping
    fun getMatchings(): List<Matching> {
        return matchingService.getAllMatchings()
    }

    /**
     * [기능 주석] 특정 매칭 방에 참여합니다.
     * 프론트엔드에서 사용자의 '내 주문 금액(JoinRequest)'을 받아오면, 서비스 단에서 1/N 배달비를 계산하여 정산 결과를 반환합니다.
     */
    @PostMapping("/{id}/join")
    fun joinMatching(
        @PathVariable id: Long,
        @RequestBody request: JoinRequest // 참여자의 아이디, 닉네임, 주문 금액 정보
    ): Map<String, Any> {
        return matchingService.joinMatching(id, request)
    }

    /**
     * [기능 주석] 특정 매칭 방을 삭제합니다.
     * 방장(개설자) 여부를 확인하기 위해 requestedUserId를 검증합니다.
     */
    @DeleteMapping("/{id}")
    fun deleteMatching(
        @PathVariable id: Long,
        @RequestParam requestedUserId: Long
    ): String {
        return matchingService.deleteMatching(id, requestedUserId)
    }

    /**
     * [기능 주석] 특정 매칭 방의 상세 정보(참여자 명단, 배달 장소 등)를 단건 조회합니다.
     */
    @GetMapping("/{id}")
    fun getMatching(@PathVariable id: Long): Matching {
        return matchingService.getMatchingById(id)
    }

    /**
     * [기능 주석] 특정 매칭 방의 정보(제목, 식당, 인원수, 마감시간 등)를 수정합니다.
     * 방장(개설자) 권한 검증 절차를 포함합니다.
     */
    @PutMapping("/{id}")
    fun updateMatching(
        @PathVariable id: Long,
        @RequestParam requestedUserId: Long,
        @RequestBody request: MatchingUpdateRequest
    ): String {
        return matchingService.updateMatching(id, requestedUserId, request)
    }
}