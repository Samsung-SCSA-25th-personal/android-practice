package com.example.android_practice

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

// =====================================================================================
// ViewModel 계층 (MVVM의 VM)
// =====================================================================================

/**
 * # CounterViewModel
 *
 * UI 상태와 비즈니스 로직을 관리하는 ViewModel입니다.
 *
 * ## 역할
 * - UI 상태(count) 관리
 * - Repository와 View 연결
 * - Activity 생명주기와 독립적 (화면 회전 시에도 데이터 유지)
 *
 * ## 생성자
 * @param _repository: 데이터 관리를 위한 Repository (의존성 주입)
 *                    - Factory 패턴으로 주입됨
 *                    - 테스트 시 Mock으로 대체 가능
 */
class CounterViewModel() : ViewModel() {

    /**
     * ## _repository: Private CounterRepository
     *
     * CounterViewModel에서 카운터 데이터를 관리하고 제공하기 위한
     * CounterRepository의 비공개 인스턴스입니다.
     *
     * ### 역할
     * - UI 계층(ViewModel)과 데이터 계층(Repository) 사이의 다리 역할
     * - ViewModel이 데이터 소스를 직접 알지 않고도 데이터에 접근하고 조작할 수 있게 함
     * - Repository 패턴을 통해 관심사 분리(Separation of Concerns) 유지
     */
    private val _repository: CounterRepository = CounterRepository()

    /**
     * ## _count: Private MutableState
     *
     * ViewModel 내부에서만 수정 가능한 가변 상태입니다.
     *
     * ### mutableStateOf()
     * - Compose의 상태 관리 함수
     * - 값 변경 시 자동으로 UI 재구성(Recomposition)
     *
     * ### 초기값
     * - repository.getCounter().count로 Repository에서 초기값 가져옴
     *
     * ### private 이유
     * - 외부에서 직접 수정 방지 (캡슐화)
     * - increment()/decrement()로만 수정
     * - 단방향 데이터 플로우 구현
     */
    private val _count = mutableStateOf(_repository.getCounter().count)

    /**
     * ## count: Public MutableState
     *
     * View에서 관찰할 수 있는 상태입니다.
     *
     * ### View에서 사용
     * ```kotlin
     * Text(text = "Count: ${viewModel.count.value}")
     * // count.value 변경 시 이 Text만 재구성됨
     * ```
     */
    val count : MutableState<Int> = _count

    /**
     * ## increment()
     *
     * 카운터 값을 1 증가시킵니다.
     *
     * ### 흐름
     * View 버튼 클릭 → viewModel.increment() → _count.value++ → UI 재구성
     */
    fun increment() {
        _repository.incrementCounter()
        _count.value = _repository.getCounter().count
    }

    /**
     * ## decrement()
     *
     * 카운터 값을 1 감소시킵니다.
     *
     * ### 동작
     * - _count.value를 1 감소
     * - increment()와 동일한 패턴 및 문제점
     */
    fun decrement() {
        _repository.decrementCounter()
        _count.value = _repository.getCounter().count
    }
}