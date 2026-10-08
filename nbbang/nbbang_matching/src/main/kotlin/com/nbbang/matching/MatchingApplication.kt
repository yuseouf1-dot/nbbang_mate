package com.nbbang.matching

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * [기본 주석] '엔빵 메이트' 애플리케이션의 백엔드(Spring Boot) 서버를 구동하는 메인 진입점(Entry Point)입니다.
 */
@SpringBootApplication
class MatchingApplication

fun main(args: Array<String>) {
    // 톰캣(Tomcat) 웹 서버를 내장하여 포트(기본 8080/8082 등)를 열고 API 요청을 대기합니다.
    runApplication<MatchingApplication>(*args)
}