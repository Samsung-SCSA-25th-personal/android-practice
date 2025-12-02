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

    private val _repository: CounterRepository = CounterRepository()
    private val _count = mutableStateOf(_repository.getCounter().count)

    val count : MutableState<Int> = _count

    fun increment() {
        _repository.incrementCounter()
        _count.value = _repository.getCounter().count
    }

    fun decrement() {
        _repository.decrementCounter()
        _count.value = _repository.getCounter().count
    }
}