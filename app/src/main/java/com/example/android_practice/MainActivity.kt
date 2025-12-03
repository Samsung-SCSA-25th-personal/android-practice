package com.example.android_practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier

/**
 * # MainActivity (MVVM의 View 계층)
 *
 * ## 📌 역할
 * - Android 앱 실행 시 **가장 먼저 실행되는 진입점**
 * - Jetpack Compose 화면을 **setContent {}** 안에 선언하여 UI를 그리는 컨테이너 역할
 * - 상태(state) 변경에 따라 UI가 자동으로 다시 그려지는 선언형 UI 환경 제공
 *
 * ## 📌 ComponentActivity를 상속하는 이유
 * - Jetpack Compose를 사용하기 위한 기본 Activity
 * - setContent { } 함수를 제공 → Compose UI 트리를 Activity 위에서 실행 가능
 * - LifecycleOwner 제공 → ViewModel 및 LiveData / StateFlow와 자연스럽게 연결됨
 *
 * ## 📌 MVVM에서 MainActivity의 위치
 * - **View** 계층
 * - ViewModel을 직접 생성하거나(권장: Hilt), viewModel()로 가져와 상태만 구독함
 * - 로직은 절대 Activity 안에 넣지 않으며, Activity는 오직 UI 프레임만 제공
 *
 * ## 📌 전체 흐름
 * 1. MainActivity → setContent {} 실행
 * 2. RecipeScreen() 호출 → ViewModel을 통해 상태(categorieState) 관찰
 * 3. ViewModel이 API 호출하여 상태 업데이트
 * 4. UI는 상태(State)에 따라 자동으로 재구성(loading / success / error)
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Jetpack Compose 진입점
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 실제 화면을 구성하는 Composable 호출
                    RecipeScreen()
                }
            }
        }
    }
}
