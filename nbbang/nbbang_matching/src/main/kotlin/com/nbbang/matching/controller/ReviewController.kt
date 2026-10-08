package com.nbbang.matching.controller

import com.nbbang.matching.domain.Review
import com.nbbang.matching.dto.ReviewCreateRequest
import com.nbbang.matching.dto.ReviewUpdateRequest
import com.nbbang.matching.service.ReviewService
import org.springframework.web.bind.annotation.*

/**
 * [기본 주석] 식당에 대한 리뷰 작성, 조회, 평균 평점 집계 등을 처리하는 REST 컨트롤러입니다.
 */
@RestController
@RequestMapping("/api/reviews")
class ReviewController(private val reviewService: ReviewService) {

    /**
     * [기능 주석] 특정 식당에 대한 리뷰(별점, 내용)를 작성합니다.
     * 작성 직후 즉시 다시 집계된 식당의 평균 평점을 반환합니다.
     */
    @PostMapping
    fun createReview(@RequestBody request: ReviewCreateRequest): Map<String, Any> {
        return reviewService.createReview(request)
    }

    /**
     * [기능 주석] 특정 식당에 작성된 모든 리뷰 목록을 조회합니다.
     */
    @GetMapping("/{restaurantName}")
    fun getReviews(@PathVariable restaurantName: String): List<Review> {
        return reviewService.getReviewsByRestaurant(restaurantName)
    }

    /**
     * [기능 주석] 특정 식당의 평균 평점(1~5점)을 집계하여 조회합니다.
     */
    @GetMapping("/{restaurantName}/rating")
    fun getAverageRating(@PathVariable restaurantName: String): Double {
        return reviewService.getRestaurantAverageRating(restaurantName)
    }

    /**
     * [기능 주석] 작성된 리뷰 내용을 수정합니다. 작성자 본인인지 확인합니다.
     */
    @PutMapping("/{id}")
    fun updateReview(
        @PathVariable id: Long,
        @RequestParam requestedUserId: Long,
        @RequestBody request: ReviewUpdateRequest
    ): String {
        return reviewService.updateReview(id, requestedUserId, request)
    }

    /**
     * [기능 주석] 작성된 리뷰를 삭제합니다. 작성자 본인인지 확인합니다.
     */
    @DeleteMapping("/{id}")
    fun deleteReview(@PathVariable id: Long, @RequestParam requestedUserId: Long): String {
        return reviewService.deleteReview(id, requestedUserId)
    }
}