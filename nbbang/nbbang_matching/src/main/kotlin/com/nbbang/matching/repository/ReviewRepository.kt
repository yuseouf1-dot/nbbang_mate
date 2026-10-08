package com.nbbang.matching.repository

import com.nbbang.matching.domain.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * [기본 주석] Review 엔티티를 데이터베이스와 연동하는 리포지토리 인터페이스입니다.
 */
interface ReviewRepository : JpaRepository<Review, Long> {

    /**
     * [기능 주석] 특정 식당(restaurantName)에 작성된 모든 리뷰 목록을 조회합니다.
     */
    fun findByRestaurantName(restaurantName: String): List<Review>

    /**
     * [기능 주석] 해당 식당의 '평균 평점'을 즉시 데이터베이스 단에서 연산(AVG)하여 반환하는 쿼리입니다.
     * ROUND 함수를 사용하여 소수점 1자리까지만 노출하도록 처리했습니다.
     */
    @Query("SELECT ROUND(AVG(r.rating), 1) FROM Review r WHERE r.restaurantName = :restaurantName")
    fun getAverageRating(restaurantName: String): Double?
}