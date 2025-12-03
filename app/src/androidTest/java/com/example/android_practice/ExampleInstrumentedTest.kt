package com.example.android_practice

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * 안드로이드 기기에서 실행될 Instrumented 테스트입니다.
 *
 * 테스팅에 대한 더 자세한 정보는 [테스팅 문서](http://d.android.com/tools/testing)를 참고하세요.
 */
// AndroidJUnit4 테스트 실행기를 사용하여 테스트를 실행합니다.
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    // 테스트 케이스임을 나타냅니다.
    @Test
    fun useAppContext() {
        // 테스트 대상 앱의 컨텍스트를 가져옵니다.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        // 앱의 패키지 이름이 "com.example.android_practice"인지 확인합니다.
        assertEquals("com.example.android_practice", appContext.packageName)
    }
}