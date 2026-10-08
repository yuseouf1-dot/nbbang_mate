package com.nbbang.matching.domain

import java.time.LocalDateTime
import jakarta.persistence.*

/**
 * [기본 주석] 매칭 방에 참여한 사용자(방장 포함)의 상세 정보를 담는 값 객체(Value Object)입니다.
 * 별도의 독립된 테이블 엔티티가 아닌, Matching 엔티티에 종속되어 관리됩니다.
 */
@Embeddable
class Participant(
    @Column(nullable = false) val userId: Long,
    @Column(nullable = false) val nickname: String,
    @Column(nullable = false) val orderAmount: Int // 개인별 주문 금액
)

/**
 * [기본 주석] 밥약 매칭 방의 핵심 정보를 관리하는 엔티티 클래스입니다.
 */
@Entity
@Table(name = "matchings")
class Matching(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column(nullable = false) var title: String,
    @Column(nullable = false) var restaurantName: String,
    @Column(nullable = false) var maxParticipants: Int,
    @Column(nullable = false) var currentParticipants: Int = 0,
    @Enumerated(EnumType.STRING) @Column(nullable = false) var status: MatchingStatus = MatchingStatus.OPEN,
    @Column var deadline: LocalDateTime? = null,
    @Column var createdByUserId: Long? = null,
    @Column(nullable = false) var deliveryFee: Int = 0,
    @Column(nullable = false) var orderType: String = "DELIVERY", // DELIVERY(배달) 또는 DINE_IN(방문)
    @Column var deliveryLocation: String? = null,

    /**
     * [기능 주석] 방에 참여한 사람들의 명단을 리스트로 관리합니다.
     * JPA의 @ElementCollection을 사용하여 'matching_participants'라는 별도 테이블로 자동 매핑됩니다.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "matching_participants", joinColumns = [JoinColumn(name = "matching_id")])
    var participants: MutableList<Participant> = mutableListOf()
) {
    /**
     * [기능 주석] 매칭 방에 새로운 사용자가 참여할 때 호출되는 비즈니스 로직입니다.
     * 방의 마감 상태, 인원수 초과 여부, 중복 참여 여부를 철저하게 검증합니다.
     */
    fun join(userId: Long, nickname: String, orderAmount: Int) {
        if (this.status == MatchingStatus.CLOSED) throw IllegalStateException("이미 마감된 매칭입니다.")
        if (this.currentParticipants >= this.maxParticipants) throw IllegalStateException("참여 인원이 꽉 찼습니다.")

        // 중복 참여 방지 로직 (1인 1회 참여 제한)
        if (this.participants.any { it.userId == userId }) {
            throw IllegalStateException("이미 참여한 매칭 방입니다.")
        }

        // 명부에 추가하고 현재 인원수 갱신
        this.participants.add(Participant(userId, nickname, orderAmount))
        this.currentParticipants = this.participants.size

        // 최대 인원에 도달하면 자동으로 방 상태를 CLOSED(마감)로 변경
        if (this.currentParticipants == this.maxParticipants) {
            this.status = MatchingStatus.CLOSED
        }
    }

    /**
     * [기능 주석] 방장 권한으로 매칭 방의 기본 정보(제목, 식당, 인원, 마감시간)를 수정하는 메서드입니다.
     */
    fun updateRoomInfo(title: String, restaurantName: String, maxParticipants: Int, deadline: LocalDateTime?) {
        // 기존에 참여한 인원보다 최대 인원을 적게 설정할 수 없도록 방어
        if (maxParticipants < this.currentParticipants) throw IllegalStateException("현재 참여 인원보다 적게 설정할 수 없습니다.")

        this.title = title
        this.restaurantName = restaurantName
        this.maxParticipants = maxParticipants
        this.deadline = deadline

        // 정원이 늘어나서 다시 모집이 가능해진 경우, 방 상태를 OPEN으로 복구
        if (this.currentParticipants < this.maxParticipants && this.status == MatchingStatus.CLOSED) {
            this.status = MatchingStatus.OPEN
        }
    }
}

enum class MatchingStatus { OPEN, CLOSED }