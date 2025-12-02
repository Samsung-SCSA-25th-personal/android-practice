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
         *
         * ### 특징
         * - 기존의 setContentView(R.layout.activity_main) 대체
         * - XML 레이아웃 대신 Kotlin 코드로 UI 작성
         * - 람다 블록 안에 모든 UI 구성 요소를 작성
         * - 선언형(Declarative) UI 프로그래밍 방식
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

            /**
             * ## MaterialTheme
             *
             * Material Design 3의 테마를 적용합니다.
             *
             * ### 제공하는 것
             * - 색상 스키마 (colorScheme): primary, secondary, background 등
             * - 타이포그래피 (typography): 텍스트 스타일
             * - 도형 (shapes): 버튼, 카드 등의 모서리 스타일
             *
             * ### 왜 필요한가?
             * - 일관된 디자인 시스템 제공
             * - 다크 모드 자동 지원
             * - Material Design 가이드라인 준수
             */
            MaterialTheme {
                /**
                 * ## Surface
                 *
                 * Material Design의 기본 컨테이너 컴포넌트입니다.
                 *
                 * ### 역할
                 * - 배경색과 elevation(그림자) 제공
                 * - 자식 컴포넌트를 담는 컨테이너
                 * - Material Design 계층 구조의 기본 단위
                 *
                 * ### modifier = Modifier.fillMaxSize()
                 * - 부모(화면 전체)를 가득 채움
                 * - width = MATCH_PARENT, height = MATCH_PARENT와 동일
                 *
                 * ### color = MaterialTheme.colorScheme.background
                 * - 테마의 배경색 사용
                 * - 다크 모드에서 자동으로 어두운 색으로 변경됨
                 */
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    /**
                     * 실제 앱의 UI 컨텐츠를 표시
                     * ViewModel을 전달하여 데이터와 로직 연결
                     */
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

    /**
     * ## Column
     *
     * 자식 컴포넌트를 세로(수직) 방향으로 배치하는 레이아웃입니다.
     *
     * ### Android View와 비교
     * - LinearLayout (orientation = vertical)과 유사
     * - 하지만 더 간결하고 선언적
     *
     * ### modifier = Modifier.fillMaxSize()
     * - 화면 전체를 채움 (너비와 높이 모두)
     * - 부모(Surface)의 크기를 그대로 사용
     */
    Column(
        modifier = Modifier.fillMaxSize(),

        /**
         * ## verticalArrangement = Arrangement.Center
         *
         * 자식 요소들을 세로 방향으로 어떻게 배치할지 결정합니다.
         *
         * ### Arrangement.Center
         * - 자식 요소들을 세로 방향 중앙에 배치
         * - 위아래 공간이 동일하게 분배됨
         *
         * ### 다른 옵션들
         * - Arrangement.Top: 위쪽 정렬
         * - Arrangement.Bottom: 아래쪽 정렬
         * - Arrangement.SpaceBetween: 요소 사이 공간 균등 분배
         * - Arrangement.SpaceAround: 요소 주변 공간 균등 분배
         */
        verticalArrangement = Arrangement.Center,

        /**
         * ## horizontalAlignment = Alignment.CenterHorizontally
         *
         * 자식 요소들을 가로 방향으로 어떻게 정렬할지 결정합니다.
         *
         * ### Alignment.CenterHorizontally
         * - 모든 자식 요소를 가로 방향 중앙에 배치
         * - 왼쪽과 오른쪽 여백이 동일
         *
         * ### 다른 옵션들
         * - Alignment.Start: 왼쪽 정렬
         * - Alignment.End: 오른쪽 정렬
         *
         * ### 결과적으로
         * - verticalArrangement + horizontalAlignment
         * - 모든 요소가 화면 정중앙에 배치됨!
         */
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        /**
         * ## Text - 카운트 값 표시
         *
         * 현재 카운트 값을 화면에 표시하는 텍스트입니다.
         *
         * ### text = "Count: ${viewModel.count.value}"
         *
         * #### viewModel.count
         * - 타입: MutableState<Int>
         * - ViewModel에서 관리하는 상태
         * - 값이 변경되면 이 Text를 포함한 Composable이 재구성됨
         *
         * #### .value
         * - MutableState에서 실제 Int 값을 가져옴
         * - count.value가 0이면 "Count: 0" 표시
         * - count.value가 5이면 "Count: 5" 표시
         *
         * #### 문자열 템플릿 ${}
         * - Kotlin의 문자열 보간(interpolation) 기능
         * - ${표현식} 형태로 값을 문자열에 삽입
         *
         * ### 재구성(Recomposition) 동작
         * 1. 사용자가 Increment 버튼 클릭
         * 2. viewModel.increment() 호출
         * 3. viewModel.count.value가 0 → 1로 변경
         * 4. Compose가 변경 감지
         * 5. 이 Text Composable만 재구성
         * 6. "Count: 0" → "Count: 1"로 화면 업데이트
         */
        Text(
            text = "Count: ${viewModel.count.value}",

            /**
             * ## fontSize = 24.sp
             *
             * 텍스트 크기를 설정합니다.
             *
             * ### sp (Scalable Pixels)
             * - 사용자의 글꼴 크기 설정을 반영하는 단위
             * - 접근성(Accessibility)을 위해 텍스트에 사용
             * - 사용자가 시스템에서 "큰 글꼴" 설정 시 자동으로 커짐
             *
             * ### dp vs sp
             * - dp: 레이아웃 크기, 여백 등에 사용 (고정 크기)
             * - sp: 텍스트 크기에 사용 (사용자 설정 반영)
             */
            fontSize = 24.sp,

            /**
             * ## fontWeight = FontWeight.Bold
             *
             * 텍스트를 굵게 표시합니다.
             *
             * ### 다른 옵션들
             * - FontWeight.Normal: 기본 두께
             * - FontWeight.Light: 얇게
             * - FontWeight.Medium: 중간 두께
             * - FontWeight.Bold: 굵게
             * - FontWeight.Black: 가장 굵게
             * - FontWeight(500): 숫자로 직접 지정 (100~900)
             */
            fontWeight = FontWeight.Bold
        )

        /**
         * ## Spacer
         *
         * UI 요소 사이에 빈 공간을 만드는 컴포넌트입니다.
         *
         * ### modifier = Modifier.height(16.dp)
         * - 세로 방향으로 16dp의 공간 생성
         * - Text와 Row(버튼들) 사이의 간격
         *
         * ### dp (Density-independent Pixels)
         * - 화면 밀도와 무관한 픽셀 단위
         * - 모든 기기에서 동일한 물리적 크기 유지
         * - 1dp ≈ 1/160 인치
         *
         * ### 왜 Spacer를 사용하나?
         * - padding보다 의도가 명확함
         * - 레이아웃 구조를 직관적으로 표현
         * - 재사용 가능한 간격 컴포넌트
         */
        Spacer(modifier = Modifier.height(16.dp))

        /**
         * ## Row
         *
         * 자식 컴포넌트를 가로(수평) 방향으로 배치하는 레이아웃입니다.
         *
         * ### Android View와 비교
         * - LinearLayout (orientation = horizontal)과 유사
         *
         * ### 기본 동작
         * - 자식 요소들을 왼쪽부터 오른쪽으로 배치
         * - 자식의 크기만큼만 공간 차지 (wrap_content)
         * - Column의 horizontalAlignment에 따라 중앙 정렬됨
         */
        Row {
            /**
             * ## Button - Increment
             *
             * 카운트를 증가시키는 버튼입니다.
             *
             * ### onClick = { viewModel.increment() }
             *
             * #### onClick: 람다 함수
             * - 버튼이 클릭되면 실행될 코드
             * - () -> Unit 타입 (파라미터 없고, 반환값 없음)
             *
             * ### 실행 흐름
             * 1. 사용자가 버튼 터치
             * 2. onClick 람다 실행
             * 3. viewModel.increment() 호출
             * 4. ViewModel에서 count.value++
             * 5. State 변경 감지
             * 6. Text Composable 재구성
             * 7. 화면에 새로운 값 표시
             */
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
