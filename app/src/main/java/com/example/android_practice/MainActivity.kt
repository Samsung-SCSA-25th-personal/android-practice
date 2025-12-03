package com.example.android_practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android_practice.navigate.FirstScreen
import com.example.android_practice.navigate.SecondScreen

/**
 * # MainActivity (MVVM - View 계층)
 *
 * ## 📌 역할
 * - Android 앱 실행 시 가장 먼저 호출되는 진입점(Entry Point)
 * - Jetpack Compose UI를 setContent {} 블록 안에서 렌더링
 * - Activity는 오직 'UI 컨테이너' 역할만 수행하며, 비즈니스 로직은 ViewModel로 분리
 *
 * ## 📌 ComponentActivity를 사용하는 이유
 * - Compose를 실행할 수 있는 기본 Activity
 * - setContent {} 제공 → Compose UI 트리 구성
 * - LifecycleOwner 제공 → ViewModel(StateFlow)과 자연스럽게 연결됨
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ⭐ Jetpack Compose 진입 지점
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 앱 전체의 NavGraph를 포함하는 Composable
                    MyApp()
                }
            }
        }
    }
}

/**
 * # MyApp()
 *
 * ## 📌 역할
 * - NavController 생성
 * - NavHost로 앱의 화면 이동(화면 라우팅) 정의
 * - 각 composable("route") 내부에서 실제 화면 호출
 *
 * ## 📌 전체 흐름
 * 1. rememberNavController() → 내비게이션 컨트롤러 생성
 * 2. NavHost → 시작 화면(startDestination)을 기준으로 Graph 구성
 * 3. composable(route) → 각 화면을 NavGraph에 매핑
 * 4. 화면에서 람다 호출로 navController.navigate("route") 실행 → 화면 이동
 */
@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "first_screen"
    ) {
        // 첫 화면
        composable("first_screen") {
            FirstScreen(
                onNext = {
                    navController.navigate("second_screen")
                }
            )
        }

        // 두 번째 화면
        composable("second_screen") {
            SecondScreen(
                onBack = {
                    navController.navigate("first_screen")
                }
            )
        }
    }
}
