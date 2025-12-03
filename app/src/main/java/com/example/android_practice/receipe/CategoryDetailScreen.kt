package com.example.android_practice.receipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

/**
 * # CategoryDetailScreen
 *
 * ## 📌 역할
 * - 선택된 카테고리(Category)의 상세 정보를 보여주는 화면
 * - 이미지 + 카테고리명 + 설명을 하나의 화면에서 표시
 *
 * ## 📌 매개변수
 * @param category
 *  - 화면에서 표시할 Category 데이터
 *  - strCategory              : 카테고리 이름
 *  - strCategoryThumb         : 카테고리 이미지 URL
 *  - strCategoryDescription   : 카테고리 설명 텍스트
 *
 * ## 📌 UI 구성
 * 1. Column: 전체 레이아웃을 수직으로 배치
 * 2. Text: 카테고리 이름을 상단 중앙에 표시
 * 3. Image: Coil의 rememberAsyncImagePainter로 네트워크 이미지 로딩
 * 4. Text: 긴 설명을 verticalScroll로 스크롤 가능하도록 처리
 *
 * ## 📌 Key Point
 * - 설명 텍스트가 길어질 수 있으므로 verticalScroll 처리 필수
 * - Image는 aspectRatio(1f)를 줘서 정사각형 비율 유지
 * - Column 전체는 padding(16.dp)로 기본 여백 확보
 */
@Composable
fun CategoryDetailScreen(category: Category) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 📌 카테고리 이름 타이틀
        Text(
            text = category.strCategory,
            textAlign = TextAlign.Center
        )

        // 📌 카테고리 이미지 (Coil 사용)
        Image(
            painter = rememberAsyncImagePainter(category.strCategoryThumb),
            contentDescription = "${category.strCategory} Thumbnail",
            modifier = Modifier
                .wrapContentSize()
                .aspectRatio(1f) // 정사각형 비율 유지
        )

        // 📌 카테고리 설명 (스크롤 가능)
        Text(
            text = category.strCategoryDescription,
            textAlign = TextAlign.Justify, // 양쪽 정렬
            modifier = Modifier.verticalScroll(rememberScrollState())
        )
    }
}
