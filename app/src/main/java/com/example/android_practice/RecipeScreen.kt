package com.example.android_practice

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

/**
 * 전체 레시피 화면을 구성하는 Composable 함수.
 * ViewModel에서 상태를 가져와 로딩/에러/정상 UI를 분기 처리한다.
 */
@Composable
fun RecipeScreen(modifier: Modifier = Modifier) {

    // ViewModel 가져오기 (Compose 내부에서 lifecycle-aware 방식)
    val recipeViewModel: MainViewModel = viewModel()

    // ViewModel의 상태 구독
    val viewState by recipeViewModel.categorieState

    Box(modifier = modifier.fillMaxSize()) {
        when {
            // 1) 로딩 상태일 때
            viewState.loading -> {
                CircularProgressIndicator(modifier.align(Alignment.Center))
            }

            // 2) 에러 상태일 때
            viewState.error != null -> {
                Text(text = "Error Occurred!", modifier = Modifier.align(Alignment.Center))
            }

            // 3) 정상적으로 데이터 로딩 완료
            else -> {
                CategoryScreen(categories = viewState.list)
            }
        }
    }
}

/**
 * 카테고리 리스트를 2열 Grid 형태로 표시하는 Composable
 */
@Composable
fun CategoryScreen(categories: List<Category>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),      // 2열 구성
        modifier = Modifier.fillMaxSize()
    ) {
        items(categories) { category ->
            CategoryItem(category = category)
        }
    }
}

/**
 * 단일 카테고리를 화면에 표시하는 Item Composable.
 * 이미지와 텍스트를 세로 방향(Column)으로 렌더링한다.
 */
@Composable
fun CategoryItem(category: Category) {

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 카테고리 썸네일 이미지
        Image(
            painter = rememberAsyncImagePainter(category.strCategoryThumb),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)   // 정사각형 비율 유지
        )

        // 카테고리 이름 텍스트
        Text(
            text = category.strCategory,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
