package com.nbbang.matching.dto

/**
 * [기본 주석] 신규 회원가입 시 사용자 정보를 받아오는 DTO입니다.
 */
data class UserRegisterRequest(
    val loginId: String,
    val password: String,
    val nickname: String
)

/**
 * [기본 주석] 로그인 시 인증을 위해 아이디와 비밀번호를 받아오는 DTO입니다.
 */
data class UserLoginRequest(
    val loginId: String,
    val password: String
)

/**
 * [기본 주석] 마이페이지에서 회원 정보(비밀번호, 닉네임)를 수정할 때 받아오는 DTO입니다.
 */
data class UserUpdateRequest(
    val password: String,
    val nickname: String
)