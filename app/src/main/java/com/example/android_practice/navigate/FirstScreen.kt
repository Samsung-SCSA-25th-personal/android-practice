package com.example.android_practice.navigate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * # FirstScreen
 *
 * ## 📌 역할
 * - 앱의 첫 번째 화면을 구성하는 Composable
 * - 사용자가 텍스트 입력 후 버튼을 눌러 두 번째 화면으로 이동하는 UI 제공
 *
 * ## 📌 매개변수
 * @param navigationToSecondScreen
 *  - 내비게이션 이동을 처리하는 콜백 함수
 *  - 화면 내부에서 NavController를 직접 참조하지 않기 위해
 *    상위(App)에서 콜백을 내려주는 **단방향 데이터 흐름(UDF)** 패턴 사용
 *
 * ## 📌 주요 기능
 * - remember + mutableStateOf 를 이용하여 간단한 UI 상태(name) 관리
 * - 중앙 정렬된 Column을 사용해 텍스트, 입력창, 버튼 배치
 */
@Composable
fun FirstScreen(onNext: (String) -> Unit) {

    // ⭐ UI 상태(value) - 화면 회전 시 유지되지 않는 간단 상태
    val name = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally // 수평 가운데 정렬
    ) {
        // 제목 텍스트
        Text(text = "This is the First Screen", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // **사용자 입력을 받는 텍스트 필드**
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it }
        )

        // ❗ 버튼 클릭 시 상위에서 전달받은 콜백 실행 → SecondScreen으로 이동
        Button(onClick = { onNext(name.value) }) {
            Text("Go to Second Screen")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FirstScreenPreview() {
    FirstScreen(onNext = {})
}
