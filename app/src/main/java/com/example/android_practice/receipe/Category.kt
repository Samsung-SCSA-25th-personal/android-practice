package com.example.android_practice.receipe

/**
 * 개별 카테고리 정보를 담는 데이터 클래스
 *
 * @property idCategory 카테고리 ID (문자열)
 * @property strCategory 카테고리 이름
 * @property strCategoryThumb 카테고리 썸네일 이미지 URL
 * @property strCategoryDescription 카테고리 상세 설명
 */
data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)

/**
 * API 응답에서 'categories' 배열을 담는 최상위 모델
 *
 * @property categories Category 리스트
 */
data class CategoriesResponse(
    val categories: List<Category>
)
