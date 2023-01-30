
plugins {
    id("com.android.library")
    id("kotlin-android")
    id("androidx.benchmark")
}

repositories {
    mavenLocal()
    google()
    mavenCentral()
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 19
        targetSdk = 19

        // Set this argument to capture profiling information, instead of measuring performance.
        // Can be one of:
        //   * None
        //   * StackSampling
        //   * MethodTracing
        // See full descriptions of available options at: d.android.com/benchmark#profiling
        //testInstrumentationRunnerArguments["androidx.benchmark.profiling.mode"] = "StackSampling"
        testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] = "ENG-BUILD,EMULATOR"
    }

    testBuildType = "release"

    buildTypes {
        getByName("release") {
            // The androidx.benchmark plugin configures release buildType with proper settings, such as:
            // - disables code coverage
            // - adds CPU clock locking task
            // - signs release buildType with debug signing config
            // - copies benchmark results into build/outputs/connected_android_test_additional_output folder
        }
    }

    packagingOptions {
        resources.excludes.add("META-INF/*")
    }

//    kotlinOptions {
//        jvmTarget = "11"
//    }
    namespace = "com.example.benchmark"
}

// [START benchmark_dependency]
dependencies {
    implementation("androidx.annotation:annotation:1.5.0")

    androidTestImplementation(project(":benchmarkable"))
    androidTestImplementation(libs.benchmark)
    // [START_EXCLUDE]

    androidTestImplementation(libs.test.ext)
    androidTestImplementation(libs.test.rules)
    androidTestImplementation(libs.test.runner)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.kotlin.stdlib)
    // [END_EXCLUDE]
}
// [END benchmark_dependency]
