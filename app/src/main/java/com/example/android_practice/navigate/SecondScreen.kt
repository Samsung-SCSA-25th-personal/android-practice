package com.example.android_practice.navigate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * # SecondScreen
 *
 * ## 📌 역할
 * - 앱의 두 번째 화면을 나타내는 Composable
 * - 버튼을 눌러 첫 번째 화면으로 다시 이동하는 기능 제공
 *
 * ## 📌 매개변수
 * @param navigationToFirstScreen
 *  - 첫 화면으로 이동시키는 콜백 함수
 *  - NavController를 화면 내부에 직접 넣지 않고, 상위(App)에서 콜백 주입 →
 *    Compose의 **단방향 데이터 흐름(UDF)** 유지
 *
 * ## 📌 UI 구성
 * - 중앙 정렬된 Column 안에 텍스트 2개 + 이동 버튼 배치
 * - 단순한 화면 구조로, 네비게이션 학습 예제용으로 적합
 */
@Composable
fun SecondScreen(name: String,
                 onBack: () -> Unit) {

    // 빈 이름 처리
    val displayName = if (name.isBlank()) "No name" else name

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,      // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally // 수평 가운데 정렬
    ) {

        // 화면 제목
        Text("This is the Second Screen", fontSize = 24.sp)

        // 간단한 메시지
        Text("Welcome $displayName", fontSize = 24.sp)

        // 버튼을 눌러 첫 번째 화면으로 이동
        Button(onClick = { onBack() }) {
            Text("Go to First Screen")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondScreenPreview() {
    SecondScreen(name = "Preview User", onBack = {})
}
