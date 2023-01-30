plugins {
    id("com.android.application")
    id("kotlin-android")
}

repositories {
    mavenLocal()
    google()
    mavenCentral()
}

android {
    namespace = "com.example.app"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.app"
        minSdk = 19
        targetSdk = 19
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    packagingOptions {
        resources.excludes.add("META-INF/*")
    }

//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
//    kotlinOptions {
//        jvmTarget = "11"
//    }
}

dependencies {
    implementation(project(":benchmarkable"))

    implementation("androidx.annotation:annotation:1.5.0")
    implementation(libs.core)

    androidTestImplementation(libs.espresso)
    androidTestImplementation(libs.test.ext)

    testImplementation(libs.junit)
}
