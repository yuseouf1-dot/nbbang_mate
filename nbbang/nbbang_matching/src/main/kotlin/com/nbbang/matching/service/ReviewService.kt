package com.nbbang.matching.service

import com.nbbang.matching.domain.Review
import com.nbbang.matching.dto.ReviewCreateRequest
import com.nbbang.matching.dto.ReviewUpdateRequest
import com.nbbang.matching.repository.ReviewRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * [기본 주석] 식당 리뷰와 관련된 비즈니스 로직(평점 계산 등)을 처리하는 서비스 클래스입니다.
 */
@Service
class ReviewService(private val reviewRepository: ReviewRepository) {

    /**
     * [기능 주석] 새로운 리뷰를 생성하고, 즉시 갱신된 해당 식당의 평균 평점을 계산하여 반환합니다.
     */
    @Transactional
    fun createReview(request: ReviewCreateRequest): Map<String, Any> {
        val review = Review(
            restaurantName = request.restaurantName,
            menuName = request.menuName,
            rating = request.rating,
            content = request.content,
            createdByUserId = request.createdByUserId
        )
        val savedReview = reviewRepository.save(review)

        // 🚀 리뷰 데이터가 DB에 저장된 직후, 평균 평점 쿼리를 다시 날려 최신 평점을 가져옵니다.
        val newAvgRating = reviewRepository.getAverageRating(request.restaurantName) ?: 0.0

        return mapOf(
            "reviewId" to savedReview.id!!,
            "avgRating" to newAvgRating
        )
    }

    /**
     * [기능 주석] 특정 식당에 남겨진 모든 리뷰를 조회합니다.
     */
    fun getReviewsByRestaurant(restaurantName: String): List<Review> {
        return reviewRepository.findByRestaurantName(restaurantName)
    }

    /**
     * [기능 주석] 특정 식당의 평균 평점을 조회합니다.
     */
    fun getRestaurantAverageRating(restaurantName: String): Double {
        return reviewRepository.getAverageRating(restaurantName) ?: 0.0
    }

    /**
     * [기능 주석] 리뷰의 내용(별점, 텍스트)을 수정합니다. 작성자 본인인지 검증합니다.
     */
    @Transactional
    fun updateReview(id: Long, requestedUserId: Long, request: ReviewUpdateRequest): String {
        val review = reviewRepository.findById(id).orElseThrow { IllegalArgumentException("리뷰가 존재하지 않습니다.") }

        if (review.createdByUserId != requestedUserId) throw IllegalStateException("작성자만 수정할 수 있습니다.")

        review.updateReview(request.rating, request.content)
        return "리뷰가 수정되었습니다."
    }

    /**
     * [기능 주석] 리뷰를 삭제합니다. 작성자 본인인지 검증합니다.
     */
    @Transactional
    fun deleteReview(id: Long, requestedUserId: Long): String {
        val review = reviewRepository.findById(id).orElseThrow { IllegalArgumentException("리뷰가 존재하지 않습니다.") }

        if (review.createdByUserId != requestedUserId) throw IllegalStateException("작성자만 삭제할 수 있습니다.")

        reviewRepository.delete(review)
        return "리뷰가 삭제되었습니다."
    }
}