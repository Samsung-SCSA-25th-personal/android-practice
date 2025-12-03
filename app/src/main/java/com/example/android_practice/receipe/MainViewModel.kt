package com.example.android_practice.receipe

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// 화면(UI)과 관련된 데이터를 관리하고 비즈니스 로직을 처리하는 ViewModel 클래스입니다.
class MainViewModel: ViewModel() {

    // 화면의 상태를 내부적으로 관리하기 위한 private 변수입니다. mutableStateOf를 사용하여 상태 변경을 감지할 수 있습니다.
    private val _categorieState = mutableStateOf(RecipeState())
    // 외부(UI)에 공개되는 읽기 전용 상태입니다. UI는 이 상태를 관찰하여 변경에 따라 화면을 다시 그립니다.
    val categorieState: State<RecipeState> = _categorieState

    // ViewModel이 생성될 때 처음으로 호출되는 초기화 블록입니다.
    init {
        // 카테고리 데이터를 가져오는 함수를 호출합니다.
        fetchCategories()
    }

    // API를 통해 카테고리 목록을 비동기적으로 가져오는 함수입니다.
    private fun fetchCategories() {
        // ViewModel의 생명주기에 맞춰 관리되는 코루틴 스코프를 사용하여 네트워크 요청을 실행합니다.
        viewModelScope.launch {
            try {
                // ApiService를 통해 카테고리 목록을 가져옵니다. (네트워크 요청)
                val response = receipeService.getCategories()
                // 요청이 성공하면 상태를 업데이트합니다.
                _categorieState.value = _categorieState.value.copy(
                    list = response.categories, // 가져온 목록으로 업데이트
                    loading = false,            // 로딩 상태를 false로 변경
                    error = null                // 에러 메시지를 null로 초기화
                )
            } catch (e: Exception) {
                // 네트워크 요청 중 오류가 발생하면 상태를 업데이트합니다.
                _categorieState.value = _categorieState.value.copy(
                    loading = false,            // 로딩 상태를 false로 변경
                    error = "Error fetching categories ${e.message}" // 에러 메시지 설정
                )

            }

        }
    }

    // 화면의 상태를 나타내는 데이터 클래스입니다.
    data class RecipeState(
        val loading: Boolean = true,            // 로딩 중인지 여부 (기본값: true)
        val list: List<Category> = emptyList(), // 카테고리 목록 (기본값: 빈 리스트)
        val error: String? = null               // 에러 메시지 (기본값: null)
    )

}