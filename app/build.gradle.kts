plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    namespace = "com.hadiyarajesh.notex"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.hadiyarajesh.notex"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"

            resValue("string", "app_name", "@string/app_name_debug")

            buildConfigField("String", "API_BASE_URL", "\"BASE_API_URL_DEV\"")
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true

            resValue("string", "app_name", "@string/app_name_release")

            buildConfigField("String", "API_BASE_URL", "\"BASE_API_URL_PROD\"")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = LibVersion.composeCompilerVersion
    }
    packagingOptions {
        resources {
            excludes += setOf("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

object LibVersion {
    const val composeVersion = "2023.01.00"
    const val composeCompilerVersion = "1.4.0"
    const val navigationComposeVersion = "2.5.3"
    const val roomVersion = "2.5.0"
    const val dataStoreVersion = "1.0.0"
    const val retrofitVersion = "2.9.0"
    const val moshiVersion = "1.14.0"
    const val accompanistVersion = "0.28.0"
    const val flowerVersion = "3.1.0"
    const val coilVersion = "2.2.2"
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:${LibVersion.composeVersion}")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation(composeBom)
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.navigation:navigation-compose:${LibVersion.navigationComposeVersion}")
    implementation("androidx.datastore:datastore-preferences:${LibVersion.dataStoreVersion}")
    implementation("androidx.paging:paging-compose:1.0.0-alpha17")
    implementation("androidx.work:work-runtime-ktx:2.7.1")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.2")

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("com.google.dagger:hilt-android:${rootProject.extra["hiltVersion"]}")
    implementation("androidx.hilt:hilt-work:1.0.0")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.extra["hiltVersion"]}")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    implementation("androidx.room:room-runtime:${LibVersion.roomVersion}")
    implementation("androidx.room:room-ktx:${LibVersion.roomVersion}")
    implementation("androidx.room:room-paging:${LibVersion.roomVersion}")
    kapt("androidx.room:room-compiler:${LibVersion.roomVersion}")

    implementation("com.squareup.retrofit2:retrofit:${LibVersion.retrofitVersion}")
    implementation("com.squareup.retrofit2:converter-moshi:${LibVersion.retrofitVersion}")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    implementation("com.squareup.moshi:moshi:${LibVersion.moshiVersion}")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:${LibVersion.moshiVersion}")

    implementation("com.google.accompanist:accompanist-swiperefresh:${LibVersion.accompanistVersion}")
    implementation("com.google.accompanist:accompanist-permissions:${LibVersion.accompanistVersion}")

    implementation("io.coil-kt:coil-compose:${LibVersion.coilVersion}") {
        because("We need an image loading library")
    }

    implementation("io.github.hadiyarajesh.flower-retrofit:flower-retrofit:${LibVersion.flowerVersion}") {
        because("We need to handle networking and database caching")
    }

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(composeBom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

// To create Kapt-generated stubs for JDK 17.
tasks.withType<org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask>().configureEach {
    kotlinOptions.jvmTarget = "17"
}
