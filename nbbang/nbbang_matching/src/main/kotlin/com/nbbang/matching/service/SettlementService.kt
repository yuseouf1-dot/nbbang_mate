package com.nbbang.matching.service

import org.springframework.stereotype.Service

/**
 * [기본 주석] 매칭 방 참여자들의 결제 금액을 계산해 주는 정산(Settlement) 전담 서비스 클래스입니다.
 */
@Service
class SettlementService {

    /**
     * [기능 주석] 🚀 제안서 핵심 기능인 'N빵 금액 자동 계산 알고리즘'입니다.
     * 총 배달비를 현재 참여 인원으로 나누어 1/N 배달비를 구하고, 개인 주문액을 더해 최종 결제 금액을 산출합니다.
     */
    fun calculateSettlement(deliveryFee: Int, currentParticipants: Int, myOrderAmount: Int): Map<String, Any> {
        val sharedFee = deliveryFee / currentParticipants // 1/N 분담 배달비 산출
        val totalPayment = myOrderAmount + sharedFee      // 내 음식 주문액 + 분담 배달비 합산

        return mapOf(
            "joined" to true,
            "myShareFee" to sharedFee, // 프론트엔드 영수증에 표시될 배달비
            "myTotal" to totalPayment  // 프론트엔드 영수증에 표시될 총액
        )
    }
}