plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
}

android {
    namespace = "self.paressz.pzdownloader"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        targetSdk = 34
        versionCode = 2
        versionName = "0.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            applicationVariants.all {
                val variant = this
                variant.outputs
                    .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
                    .forEach { output ->
                        val fileName = "PzDownloader-${variant.versionName}.apk"
                        output.outputFileName = fileName
                    }
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }

}

dependencies {
    implementation(androidx.core.ktx)
    implementation(androidx.appcompat)
    implementation(androidx.material)
    implementation(androidx.activity)
    implementation(androidx.constraintlayout)
    implementation(project(":core"))
    testImplementation(libs.junit)
    androidTestImplementation(androidx.junit)
    androidTestImplementation(androidx.espresso.core)

    //LIFECYCLE
    implementation(androidx.viewmodel)
    implementation(androidx.livedata)

    //KOTLINX
    implementation(kotlinx.kotlinx.coroutine)

    //Glide
    implementation(libs.glide)

    //HILT
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    //Ketch
    implementation(libs.ketch)
}
kapt {
    correctErrorTypes = true
}