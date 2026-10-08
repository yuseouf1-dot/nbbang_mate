package com.nbbang.matching.service

import com.nbbang.matching.domain.User
import com.nbbang.matching.dto.UserRegisterRequest
import com.nbbang.matching.dto.UserUpdateRequest
import com.nbbang.matching.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * [기본 주석] 플랫폼 사용자의 인증(로그인) 및 계정 관리를 담당하는 서비스 클래스입니다.
 */
@Service
class UserService(private val userRepository: UserRepository) {

    /**
     * 🚀 CRUD (1) - Create: 회원가입 기능
     * [기능 주석] 새로운 사용자를 등록합니다. 입력받은 아이디(loginId)가 이미 존재하는지 중복 검사를 수행합니다.
     */
    @Transactional
    fun registerUser(request: UserRegisterRequest): Long {
        if (userRepository.findByLoginId(request.loginId) != null) {
            throw IllegalArgumentException("이미 존재하는 아이디입니다.")
        }
        val user = User(
            loginId = request.loginId,
            password = request.password,
            nickname = request.nickname
        )
        return userRepository.save(user).id!!
    }

    /**
     * 🚀 CRUD (2) - Read: 로그인 기능
     * [기능 주석] 아이디의 존재 여부를 먼저 확인하고, 비밀번호가 일치하는지 대조하는 인증 로직입니다.
     */
    @Transactional(readOnly = true)
    fun login(loginId: String, password: String): User {
        val user = userRepository.findByLoginId(loginId)
            ?: throw IllegalArgumentException("존재하지 않는 아이디입니다.")

        // 비밀번호 일치 확인
        if (user.password != password) {
            throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        }
        return user
    }

    /**
     * 🚀 CRUD (3) - Update: 사용자 정보 수정 기능
     * [기능 주석] 마이페이지에서 새로운 닉네임과 비밀번호를 받아 회원 정보를 갱신합니다.
     */
    @Transactional
    fun updateUser(id: Long, request: UserUpdateRequest): String {
        val user = userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("해당 사용자를 찾을 수 없습니다.") }

        user.updateProfile(request.password, request.nickname)
        return "사용자 정보가 성공적으로 수정되었습니다."
    }

    /**
     * 🚀 CRUD (4) - Delete: 사용자 탈퇴 기능
     * [기능 주석] 서비스에서 회원 탈퇴 시, DB에서 유저 정보를 영구 삭제합니다.
     */
    @Transactional
    fun deleteUser(id: Long): String {
        if (!userRepository.existsById(id)) {
            throw IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        }
        userRepository.deleteById(id)
        return "회원 탈퇴가 성공적으로 완료되었습니다."
    }
}