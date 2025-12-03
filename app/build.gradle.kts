plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.android_practice"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.android_practice"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Compose ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Network calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Json to Kotlin object mapping
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Image loading
    implementation("io.coil-kt:coil-compose")

    // Junit
    // 1. AndroidX Test: JUnit4 테스트 러너를 Android 환경에서 사용 가능하게 합니다.
    // @RunWith(AndroidJUnit4::class) 사용을 위해 필수적입니다.
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // 최신 버전을 사용하세요.

    // 2. Android Test Core: InstrumentationRegistry 및 테스트 기본 API 접근을 위해 필요합니다.
    androidTestImplementation("androidx.test:runner:1.5.2") // 최신 버전을 사용하세요.

    // 3. Espresso Core (선택적): UI 상호작용 테스트 프레임워크입니다.
    // 기본 컨텍스트 테스트에는 필수는 아니지만, 보통 함께 포함됩니다.
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // 4. JUnit Core: 단위 테스트의 기본 Assertion (assertEquals 등)을 제공합니다.
    // (일반적으로 testImplementation 스코프에 있지만, 명확성을 위해 포함합니다.)
    testImplementation("junit:junit:4.13.2")

}
