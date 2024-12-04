plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.classwork"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.classwork"
        minSdk = 24
        targetSdk = 34
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

    buildFeatures {
        viewBinding = true // Исправлено
    }
}

dependencies {
    // Glide для работы с изображениями
    implementation("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1") // Обработчик аннотаций Glide

    // PaperDB для хранения данных
    implementation("io.github.pilgr:paperdb:2.7.2")

    // Основные Android-библиотеки
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Тестирование
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Edge-to-Edge (если требуется)
    implementation("androidx.core:core-splashscreen:1.0.1") // Для Edge-to-Edge
}
