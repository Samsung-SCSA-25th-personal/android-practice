package com.example.android_practice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Retrofit 인스턴스를 생성하고 설정하는 최상위(Top-Level) 객체입니다.
 * 이 객체들은 앱의 수명 주기 동안 단 한 번만 초기화됩니다 (싱글톤 패턴).
 */

// Retrofit 인스턴스: HTTP 통신을 위한 기본적인 설정 담당
private val retrofit = Retrofit.Builder()
    // API의 기본 URL을 설정합니다. 모든 엔드포인트는 이 URL을 기준으로 경로가 추가됩니다.
    .baseUrl("https://www.themealdb.com/api/json/v1/1/")
    // JSON 응답 본문(Response Body)을 Kotlin 데이터 클래스(CategoriesResponse)로 자동 변환하기 위해 Gson 컨버터 팩토리를 추가합니다.
    .addConverterFactory(GsonConverterFactory.create())
    // 설정된 내용을 바탕으로 Retrofit 객체를 빌드합니다.
    .build()

// Retrofit 인스턴스를 사용하여 ApiService 인터페이스의 동적 구현체를 생성합니다.
// 앱 전체에서 API 호출을 수행하는 데 사용되는 서비스 객체입니다.
val receipeService: ApiService = retrofit.create(ApiService::class.java)

/**
 * Retrofit을 사용하여 정의된 API 엔드포인트(Endpoint) 인터페이스입니다.
 * 모든 서버 요청 함수(HTTP 메서드)를 선언합니다.
 */
interface ApiService {

    /**
     * HTTP GET 요청을 통해 음식 카테고리 목록을 서버로부터 가져옵니다.
     * @GET 어노테이션의 값("categories.php")은 baseUrl 뒤에 붙는 상대 경로입니다.
     * * @return CategoriesResponse: 서버 응답 JSON을 매핑한 데이터 모델 객체
     *
     * suspend 키워드: 이 함수가 Kotlin 코루틴(Coroutine) 내에서만 호출 가능하며,
     * 네트워크 I/O 작업을 비동기적으로 처리하여 메인 스레드를 블록하지 않음을 나타냅니다.
     */
    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse

}
