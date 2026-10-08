package com.nbbang.matching.domain

import jakarta.persistence.*

/**
 * [기본 주석] 플랫폼 서비스 이용자의 회원 정보를 관리하는 핵심 엔티티 클래스입니다.
 */
@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    var loginId: String, // 로그인에 사용되는 고유 아이디 (예: 학번)

    @Column(nullable = false)
    var password: String, // 로그인 시 검증할 비밀번호

    @Column(nullable = false)
    var nickname: String // 서비스 내에서 표시될 활동명
) {
    /**
     * [기능 주석] 사용자가 마이페이지에서 본인의 프로필(비밀번호 및 닉네임)을 수정할 때 호출되는 메서드입니다.
     */
    fun updateProfile(newPassword: String, newNickname: String) {
        this.password = newPassword
        this.nickname = newNickname
    }
}