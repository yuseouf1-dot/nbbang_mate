package com.nbbang.matching.domain

import jakarta.persistence.*

/**
 * [기본 주석] 특정 식당에 대해 사용자가 남긴 리뷰와 별점 정보를 저장하는 엔티티 클래스입니다.
 */
@Entity
@Table(name = "reviews")
class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var restaurantName: String, // 리뷰 대상 식당 이름

    @Column(nullable = false)
    var menuName: String, // 주문한 메뉴 이름

    @Column(nullable = false)
    var rating: Int, // 부여한 별점 (1~5점)

    @Column(nullable = false, length = 500)
    var content: String, // 리뷰 상세 내용

    @Column(nullable = false)
    var createdByUserId: Long // 리뷰 작성자의 식별자(ID)
) {
    /**
     * [기능 주석] 리뷰 작성자가 본인의 리뷰 내용(별점 및 텍스트)을 수정할 때 사용하는 로직입니다.
     */
    fun updateReview(rating: Int, content: String) {
        this.rating = rating
        this.content = content
    }
}