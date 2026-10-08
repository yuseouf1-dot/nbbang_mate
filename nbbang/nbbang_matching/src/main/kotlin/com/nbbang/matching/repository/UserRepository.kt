package com.nbbang.matching.repository

import com.nbbang.matching.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * [기본 주석] User 엔티티의 데이터 접근을 처리하는 리포지토리입니다.
 */
@Repository
interface UserRepository : JpaRepository<User, Long> {

    /**
     * [기능 주석] 로그인 및 회원가입 시 중복 검사를 위해 'loginId'를 기준으로 사용자 정보를 찾는 메서드입니다.
     */
    fun findByLoginId(loginId: String): User?
}