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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RecipeScreen()
                }
            }
        }
    }
}
