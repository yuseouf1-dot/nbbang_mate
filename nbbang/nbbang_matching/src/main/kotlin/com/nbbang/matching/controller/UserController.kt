package com.nbbang.matching.controller

import com.nbbang.matching.domain.User
import com.nbbang.matching.dto.UserLoginRequest
import com.nbbang.matching.dto.UserRegisterRequest
import com.nbbang.matching.dto.UserUpdateRequest
import com.nbbang.matching.service.UserService
import org.springframework.web.bind.annotation.*

/**
 * [기본 주석] 사용자의 회원가입, 로그인, 정보 수정 및 탈퇴를 처리하는 REST 컨트롤러입니다.
 */
@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    /**
     * [기능 주석] 신규 사용자 회원가입 처리. ID의 중복 여부를 서비스 단에서 검증합니다.
     */
    @PostMapping("/register")
    fun register(@RequestBody request: UserRegisterRequest): String {
        val id = userService.registerUser(request)
        return "회원가입 완료! (User ID: $id)"
    }

    /**
     * [기능 주석] 로그인 처리. 아이디와 비밀번호가 일치하면 해당 사용자 객체(User) 전체를 JSON으로 반환합니다.
     */
    @PostMapping("/login")
    fun login(@RequestBody request: UserLoginRequest): User {
        return userService.login(request.loginId, request.password)
    }

    /**
     * [기능 주석] 사용자의 프로필 정보(비밀번호, 닉네임 등)를 수정합니다.
     */
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: UserUpdateRequest): String {
        return userService.updateUser(id, request)
    }

    /**
     * [기능 주석] 회원 탈퇴를 처리합니다. 데이터베이스에서 사용자 정보를 삭제합니다.
     */
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): String {
        return userService.deleteUser(id)
    }
}