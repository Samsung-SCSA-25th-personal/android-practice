package com.example.android_practice

// =====================================================================================
// Model 계층 (MVVM의 M)
// =====================================================================================

/**
 * # CounterModel (Data Class)
 *
 * 카운터 데이터를 담는 모델 클래스입니다.
 *
 * ## Spring과 비교
 * - Spring의 VO (Value Object) / DTO와 유사
 * - 순수한 데이터만 담는 객체 (비즈니스 로직 없음)
 *
 * ## data class란?
 * Kotlin의 특별한 클래스로, 자동으로 다음을 생성해줍니다:
 * - equals() / hashCode() - 값 비교
 * - toString() - "CounterModel(count=5)"
 * - copy() - 객체 복사
 *
 * ## 파라미터
 * @property count: 카운터의 현재 값
 *                  - var: 값 변경 가능 (counter.count++)
 *                  - val이면: 값 변경 불가, 매번 새 객체 생성해야 함
 */
data class CounterModel(var count : Int)

// =====================================================================================
// Repository 계층 (MVVM의 Model - 데이터 관리)
// =====================================================================================

/**
 * # CounterRepository
 *
 * 카운터 데이터를 관리하고 제공하는 Repository입니다.
 *
 * ## MVVM에서의 역할
 * - ViewModel과 실제 데이터 사이의 중간 계층
 * - 데이터의 CRUD 담당 (실제 앱에서는 DB, API, 로컬 저장소 등 관리)
 * - ViewModel은 데이터가 어디서 오는지 몰라도 됨 (관심사의 분리)
 *
 * ## Repository 패턴의 장점
 * 1. ViewModel은 데이터 출처를 몰라도 됨
 * 2. 테스트 시 Repository를 Mock으로 대체 가능
 * 3. 데이터 소스 변경 시 Repository만 수정하면 됨
 *
 * ## Spring과 비교
 * - Spring의 @Repository와 동일한 역할
 * - 데이터 접근 계층(Data Access Layer)
 */
class CounterRepository {

    /**
     * ## counter: CounterModel 인스턴스
     *
     * 실제 카운터 데이터를 담고 있는 객체입니다.
     *
     * ### private
     * - 외부에서 직접 접근 불가 (캡슐화)
     * - Repository 메서드를 통해서만 접근 가능
     *
     * ### var vs val
     * - var: counter 자체를 다른 객체로 교체 가능 (현재는 안 함)
     * - 실제로는 counter.count만 변경
     *
     * ### 초기값 = CounterModel(0)
     * - Repository 생성 시 count를 0으로 시작
     *
     * ### 실제 앱에서는?
     * 메모리 대신 DB, API, SharedPreferences 등에서 데이터 가져옴
     * ```kotlin
     * class CounterRepository(private val dao: CounterDao) {
     *     suspend fun getCounter() = dao.getCounter() ?: CounterModel(0)
     * }
     * ```
     */
    private var _counter = CounterModel(0)

    fun getCounter() : CounterModel {
        return _counter
    }

    fun incrementCounter() {
        _counter.count++
    }

    fun decrementCounter() {
        _counter.count--
    }
}

/**
 * ## 📊 간단 요약
 *
 * ### CounterModel (데이터 클래스)
 * - **역할**: 카운터 값(count)을 담는 그릇
 * - **Spring 비유**: VO/DTO
 * - **특징**: data class로 equals, toString 등 자동 생성
 *
 * ### CounterRepository (데이터 관리)
 * - **역할**: CounterModel을 관리하고 CRUD 제공
 * - **Spring 비유**: @Repository
 * - **메서드**:
 * - `getCounter()`: 현재 데이터 반환
 * - `incrementCounter()`: count 증가
 * - `decrementCounter()`: count 감소
 *
 * ### 데이터 흐름
 *
 * Repository (데이터 저장)
 * ↕
 * ViewModel (비즈니스 로직)
 * ↕
 * View (UI 표시)
 */
