plugins {
    id("com.android.library")
    id("kotlin-android")
}

repositories {
    mavenLocal()
    google()
    mavenCentral()
}

android {
    compileSdk = 33
    namespace = "com.example.benchmark.ui"

    defaultConfig {
        minSdk = 19
        targetSdk = 19
    }

    packagingOptions {
        resources.excludes.add("META-INF/*")
    }

//    kotlinOptions {
//        jvmTarget = "11"
//    }
}

dependencies {
    val JACKSON_VERSION = "2.7.9" // com.fasterxml.jackson.core:jackson-core
    // com.fasterxml.jackson.core:jackson-annotations
    val JACKSON_DATABIND_VERSION = "2.7.9.7"  // com.fasterxml.jackson.core:jackson-databind

    api("com.google.code.gson:gson:2.10")
    api("com.owlike:genson:1.6")
    api("org.apache.tapestry:tapestry-json:5.8.2")
    api("com.alibaba:fastjson:1.2.83_noneautotype")
    api("com.fasterxml.jackson.core:jackson-core:$JACKSON_VERSION")
    api("com.fasterxml.jackson.core:jackson-databind:$JACKSON_DATABIND_VERSION")
    api("net.minidev:json-smart:2.4.8")
    //api("com.squareup.moshi:moshi:1.14.0") assumes you want double for all numbers
    api("com.jsoniter:jsoniter:0.9.23")
    api("com.eclipsesource.minimal-json:minimal-json:0.9.5")
    api("org.jodd:jodd-json:6.0.3")
    api("com.grack:nanojson:1.7")
    api("com.fasterxml.jackson.jr:jackson-jr-objects:2.13.0")
//    api("com.googlecode.json-simple:json-simple:1.1.1") slow and forces conflicting junit version
//    api("org.sharegov:mjson:1.4.0") has memory leak?

    implementation(libs.kotlin.stdlib)
    implementation("androidx.annotation:annotation:1.5.0")

    androidTestImplementation(libs.espresso)
    androidTestImplementation(libs.test.ext)
    androidTestImplementation(libs.test.runner)

    testImplementation(libs.junit)
}
