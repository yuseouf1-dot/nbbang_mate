package com.nbbang.matching.dto

/**
 * [기본 주석] 새로운 리뷰를 작성할 때 필요한 정보를 담는 DTO입니다.
 */
data class ReviewCreateRequest(
    val restaurantName: String,
    val menuName: String,
    val rating: Int,
    val content: String,
    val createdByUserId: Long
)

/**
 * [기본 주석] 기존 리뷰를 수정할 때 필요한 정보를 담는 DTO입니다. (별점, 내용만 변경 가능)
 */
data class ReviewUpdateRequest(
    val rating: Int,
    val content: String
)