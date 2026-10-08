package com.nbbang.matching.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.CharacterEncodingFilter

/**
 * [기본 주석] 웹 요청 및 응답의 문자 인코딩을 설정하는 설정 클래스입니다.
 */
@Configuration
class FilterConfig {

    /**
     * [기능 주석] 애플리케이션 전역에서 한글 깨짐을 방지하기 위해 강제로 UTF-8 인코딩을 적용하는 필터를 빈(Bean)으로 등록합니다.
     */
    @Bean
    fun characterEncodingFilter(): CharacterEncodingFilter {
        val filter = CharacterEncodingFilter()
        filter.encoding = "UTF-8"
        filter.setForceEncoding(true) // HTTP 요청 및 응답 모두에 UTF-8을 강제 적용
        return filter
    }
}