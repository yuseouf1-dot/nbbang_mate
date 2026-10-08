package com.nbbang.matching.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * [기본 주석] 프론트엔드(React)와의 원활한 API 통신을 위한 CORS(교차 출처 리소스 공유) 설정 클래스입니다.
 */
@Configuration
class WebConfig : WebMvcConfigurer {

    /**
     * [기능 주석] 허용할 출처(Origin), HTTP 메서드 등을 설정하여 브라우저의 CORS 에러를 방지합니다.
     */
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**") // 모든 API 경로에 대하여 CORS를 허용
            .allowedOrigins("http://localhost:3000", "http://172.29.112.1:3000") // React 개발 서버 주소 허용
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드 설정
            .allowedHeaders("*") // 모든 HTTP 헤더 허용
            .allowCredentials(true) // 쿠키 및 인증 정보(세션/JWT 등)를 포함한 요청 허용
    }
}