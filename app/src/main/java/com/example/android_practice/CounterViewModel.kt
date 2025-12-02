package com.example.android_practice

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CounterViewModel : ViewModel() {

    // 가변 상태를 사용하여 카운터 값을 저장합니다.
    private val _count = mutableStateOf(0)

    // 불변 상태로 외부로 변수를 노출합니다.
    // _count -> count
    val count : MutableState<Int> = _count

    fun increment() {
        _count.value++; // private 변수를 수정
    }

    fun decrement() {
        _count.value--;
    }

}