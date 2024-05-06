import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
}

val localProperties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}
val kakaoNativeAppKey = localProperties.getProperty("kakao_native_app_key")

android {
    namespace = "com.example.pathfinder"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pathfinder"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        //Gradle 스크립트에서 local.properties 파일을 읽고,
        // 그 값을 AndroidManifest.xml 파일에서 사용할 수 있도록 manifestPlaceholders에 값을 설정할 수 있습니다.
        val properties = Properties().apply {
            FileInputStream(File(rootDir, "local.properties")).use { load(it) }
        }
        val kakaoNativeAppKey = properties.getProperty("kakao_native_app_key")
        manifestPlaceholders["kakao_native_app_key"] = kakaoNativeAppKey

        // 빌드 타입에 따라 다른 값을 설정할 수 있습니다.
        buildConfigField("String", "kakao_native_app_key", "\"$kakaoNativeAppKey\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
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

    viewBinding {
        enable = true
    }

}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.kakao.sdk:v2-user:2.8.6")
    implementation("androidx.preference:preference:1.2.0")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}