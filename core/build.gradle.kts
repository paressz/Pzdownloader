import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.library" )
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
}
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if(localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}
val GITHUB_TOKEN = localProperties.getProperty("GITHUB_TOKEN") ?: ""
android {
    namespace = "self.paressz.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "GITHUB_TOKEN", "\"${GITHUB_TOKEN}\"")
        }
        debug {
            buildConfigField("String", "GITHUB_TOKEN", "\"${GITHUB_TOKEN}\"")
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
        buildConfig = true
    }
}
kapt {
    correctErrorTypes = true
}
dependencies {

    implementation(androidx.core.ktx)
    implementation(androidx.appcompat)
    implementation(androidx.material)
    testImplementation(libs.junit)
    androidTestImplementation(androidx.junit)
    androidTestImplementation(androidx.espresso.core)

    //mockito
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)

    //NETWORK
    implementation(libs.retrofit)
    implementation(libs.gsonConverter)
    implementation(libs.loggingInterceptor)
    //HILT
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}