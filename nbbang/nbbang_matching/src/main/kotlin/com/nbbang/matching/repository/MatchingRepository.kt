package com.nbbang.matching.repository

import com.nbbang.matching.domain.Matching
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * [기본 주석] Matching 엔티티의 데이터베이스 접근을 담당하는 JPA 리포지토리입니다.
 * 기본 제공되는 JpaRepository를 상속받아 기본적인 CRUD 기능(save, findAll, findById 등)을 자동 지원받습니다.
 */
@Repository
interface MatchingRepository : JpaRepository<Matching, Long> {
    // 나중에 "특정 식당 이름으로 매칭 방 검색" 같은 기능이 필요할 때
    // 여기에 커스텀 쿼리 메서드를 추가할 수 있습니다.
}