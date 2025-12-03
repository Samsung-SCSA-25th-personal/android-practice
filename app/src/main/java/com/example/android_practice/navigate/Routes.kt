package com.example.android_practice.navigate

import kotlinx.serialization.Serializable

/**
 * # Navigation Routes
 *
 * ## 📌 역할
 * - Type-Safe Navigation을 위한 Serializable 라우트 객체 정의
 * - Kotlin Serialization을 사용하여 타입 안전한 네비게이션 제공
 *
 * ## 📌 장점
 * - 컴파일 타임에 오류 감지 가능
 * - 문자열 라우트 대신 타입 안전한 객체 사용
 * - IDE 자동완성 및 리팩토링 지원
 * - 파라미터 타입 검증 자동화
 */

/**
 * 첫 번째 화면 라우트
 * - 파라미터가 없는 단순 화면
 */
@Serializable
object FirstScreenRoute

/**
 * 두 번째 화면 라우트
 * @param name 첫 화면에서 전달받은 사용자 이름
 */
@Serializable
data class SecondScreenRoute(val name: String)
