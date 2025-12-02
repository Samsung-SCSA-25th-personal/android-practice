package com.example.android_practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

// =====================================================================================
// View 계층 - MVVM 패턴의 View 부분
// =====================================================================================

/**
 * # MainActivity (MVVM의 View 계층)
 *
 * ## 역할
 * - Android 앱의 진입점 (앱 실행 시 가장 먼저 실행됨)
 * - Jetpack Compose UI를 설정하고 시작하는 컨테이너
 * - 화면의 생명주기를 관리 (onCreate, onStart, onResume 등)
 *
 * ## ComponentActivity를 상속하는 이유
 * - Jetpack Compose를 사용하기 위한 기본 Activity
 * - setContent {} 함수를 제공하여 선언형 UI 작성 가능
 * - ViewModel, Lifecycle 등 Jetpack 라이브러리와 통합
 */
class MainActivity : ComponentActivity() {

    /**
     * ## onCreate()
     *
     * Activity의 생명주기에서 가장 먼저 호출되는 메서드입니다.
     *
     * ### 호출 시점
     * - 앱이 처음 실행될 때
     * - 화면 회전 등으로 Activity가 재생성될 때
     *
     * @param savedInstanceState: 이전 상태를 복원하기 위한 데이터
     *                            - 화면 회전 시 데이터 보존
     *                            - null이면 새로 시작하는 것
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // 부모 클래스의 초기화 먼저 실행

        /**
         * ## setContent { }
         *
         * Jetpack Compose UI를 설정하는 함수입니다.
         */
        setContent {
            /**
             * ## ViewModel 생성
             *
             * viewModel() 함수로 CounterViewModel 인스턴스를 가져옵니다.
             *
             * ### 동작 방식
             * 1. 처음 호출 시: 새로운 CounterViewModel 인스턴스 생성
             * 2. 재구성(Recomposition) 시: 기존 인스턴스 재사용
             * 3. 화면 회전 시: 동일한 인스턴스 유지 (데이터 보존!)
             *
             * ### 왜 여기서 생성하나?
             * - Activity 범위의 ViewModel이므로 Activity가 살아있는 동안 유지
             * - setContent 블록 안에서 생성하면 Compose가 생명주기 자동 관리
             *
             * ### 타입 명시 이유
             * val viewModel: CounterViewModel = viewModel()
             * - 코드 가독성 향상
             * - IDE의 자동완성 및 타입 체크 지원
             * - 명시적으로 어떤 ViewModel인지 표시
             */
            val viewModel : CounterViewModel = viewModel()

            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TheCounterApp(viewModel)
                }
            }
        }
    }
}

// =====================================================================================
// UI Components - 실제 화면을 구성하는 Composable 함수들
// =====================================================================================

/**
 * # TheCounterApp
 *
 * 카운터 앱의 메인 화면을 구성하는 Composable 함수입니다.
 *
 * ## MVVM 패턴에서의 역할
 * - View 계층: ViewModel의 데이터를 화면에 표시
 * - 사용자 입력(버튼 클릭)을 ViewModel에 전달
 * - 상태가 변경되면 자동으로 UI 재구성(Recomposition)
 *
 * ## 파라미터
 * @param viewModel: CounterViewModel 인스턴스
 *                   - count 상태를 읽어옴
 *                   - increment(), decrement() 함수 호출
 *
 * ## 특징
 * - Stateless Composable: 자체적으로 상태를 관리하지 않음
 * - ViewModel에 의존하여 데이터와 로직을 처리
 * - 재사용 가능하고 테스트하기 쉬운 구조
 */
@Composable
fun TheCounterApp(viewModel : CounterViewModel) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Count: ${viewModel.count.value}",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = { viewModel.increment() }) {
                Text(text = "Increment")
            }
            Button(onClick = { viewModel.decrement() }) {
                Text(text = "Decrement")
            }
        }
    }
}

/**
 * ## 📊 전체 구조 다이어그램
 * ```
 * MainActivity (Activity 생명주기)
 * │
 * └─ setContent { } (Compose UI 시작)
 * │
 * ├─ viewModel: CounterViewModel (상태 관리)
 * │
 * └─ MaterialTheme (테마 적용)
 * │
 * └─ Surface (배경 컨테이너)
 * │
 * └─ TheCounterApp(viewModel) (메인 UI)
 * │
 * └─ Column (세로 레이아웃)
 * │
 * ├─ Text ("Count: X")
 * │   └─ viewModel.count.value 관찰
 * │
 * ├─ Spacer (16dp 간격)
 * │
 * └─ Row (가로 레이아웃)
 * │
 * ├─ Button ("Increment")
 * │   └─ onClick → viewModel.increment()
 * │
 * └─ Button ("Decrement")
 * └─ onClick → viewModel.decrement()
 * ```
 *
 * ---
 *
 * ## 🔄 데이터 플로우 (MVVM 패턴)
 * ```
 * [View] TheCounterApp
 * │
 * │ (1) 버튼 클릭 이벤트 발생
 * ↓
 * onClick = { viewModel.increment() }
 * │
 * │ (2) ViewModel의 함수 호출
 * ↓
 * [ViewModel] CounterViewModel
 * │
 * │ (3) 상태 변경
 * ↓
 * count.value++ (0 → 1)
 * │
 * │ (4) State 변경 감지 (Compose 프레임워크)
 * ↓
 * [View] Text Composable 재구성 (Recomposition)
 * │
 * │ (5) 새로운 값으로 UI 업데이트
 * ↓
 * 화면에 "Count: 1" 표시
 */
