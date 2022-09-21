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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
    const val composeVersion = "1.2.0"
    const val composeCompilerVersion = "1.2.0"
    const val navigationComposeVersion = "2.5.1"
    const val roomVersion = "2.4.2"
    const val dataStoreVersion = "1.0.0"
    const val retrofitVersion = "2.9.0"
    const val moshiVersion = "1.13.0"
    const val accompanistVersion = "0.25.0"
}

dependencies {
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.activity:activity-compose:1.5.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("androidx.compose.ui:ui:${LibVersion.composeVersion}")
    implementation("androidx.compose.ui:ui-tooling-preview:${LibVersion.composeVersion}")
    implementation("androidx.compose.material3:material3:1.0.0-alpha16")
    implementation("androidx.navigation:navigation-compose:${LibVersion.navigationComposeVersion}")
    implementation("androidx.datastore:datastore-preferences:${LibVersion.dataStoreVersion}")
    implementation("androidx.paging:paging-compose:1.0.0-alpha16")
    // DO NOT upgrade desugar_jdk_libs to 1.2.0
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.6")

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("com.google.dagger:hilt-android:${rootProject.extra["hiltVersion"]}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.extra["hiltVersion"]}")

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

    implementation("io.coil-kt:coil-compose:2.1.0") {
        because("We need image loading library")
    }

//    implementation("io.github.hadiyarajesh:flower:2.0.3") {
//        because("We need networking and database caching")
//    }

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${LibVersion.composeVersion}")
    debugImplementation("androidx.compose.ui:ui-tooling:${LibVersion.composeVersion}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${LibVersion.composeVersion}")


    implementation("androidx.work:work-runtime-ktx:2.7.1")
    implementation("androidx.hilt:hilt-work:1.0.0")

    kapt("androidx.hilt:hilt-compiler:1.0.0")
}
