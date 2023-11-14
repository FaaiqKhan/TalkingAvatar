plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.practice.talkingavatar"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.practice.talkingavatar"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val addConstant = { constantName: String, constantValue: String ->
            buildConfigField("String", constantName, constantValue)
        }

        addConstant("S3_REGION", "\"ap-southeast-1\"")
        addConstant("S3_ACCESS_KEY", "\"AKIA6JAJNGO6KI2BGD2P\"")
        addConstant("S3_SECRET_KEY", "\"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\"")
        addConstant("S3_BUCKET_NAME", "\"aipoctw\"")
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
    flavorDimensions += "talking_avatar"
    productFlavors {
        create("techwardsAI") {
            dimension = "talking_avatar"
            versionCode = 1
            versionName = "1.0"
            applicationIdSuffix = ".techwardsai"

            val addConstant = { constantName: String, constantValue: String ->
                buildConfigField("String", constantName, constantValue)
            }

            addConstant("D_ID_USERNAME", "\"c3Vic2NyaXB0aW9uc0B3ZWJhZmZpbml0eS5jb20\"")
            addConstant("D_ID_PASSWORD", "\"8MVwfXoQdjhxXSRJY76H6\"")
            addConstant("ELEVEN_LAB_API_KEY", "\"686d0d885e023b521eb4e143ac4f5583\"")
            addConstant("CHAT_BOT_ID", "\"e9385531-ac12-4ca5-8293-4f6903d7b2e2\"")
            addConstant("ORGANIZATION_ID","\"94e1187d-78cc-47af-8949-c39e4edc2136\"")
        }
        create("mateAI") {
            dimension = "talking_avatar"
            versionCode = 1
            versionName = "1.0"
            applicationIdSuffix = ".mateai"

            val addConstant = { constantName: String, constantValue: String ->
                buildConfigField("String", constantName, constantValue)
            }

            addConstant("D_ID_USERNAME", "\"cmF6YUBzYmNnLmNv\"")
            addConstant("D_ID_PASSWORD", "\"t2Eol2qCv-60XSuQ0jnf2\"")
            addConstant("ELEVEN_LAB_API_KEY", "\"37dd7022cc40e6b40692c6a2926843c6\"")
            addConstant("CHAT_BOT_ID", "\"4e43bbe8-61a8-434b-b6e4-f94af3b131a8\"")
            addConstant("ORGANIZATION_ID","\"2cea0d8d-2d11-411b-b5db-ef4170019c2c\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.media3:media3-exoplayer:1.1.1")
    implementation("androidx.media3:media3-exoplayer-dash:1.1.1")
    implementation("androidx.media3:media3-ui:1.1.1")
    implementation("androidx.compose.ui:ui-viewbinding:1.5.4")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.45")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    implementation("androidx.navigation:navigation-compose:2.7.5")
    implementation("io.coil-kt:coil-compose:2.4.0")
    // Network
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.moshi:moshi:1.11.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.11.0")
    // AWS S3 bucket dependency
    implementation("com.amazonaws:aws-android-sdk-s3:2.17.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
    implementation("io.arrow-kt:arrow-core:1.2.0")
    implementation("io.arrow-kt:arrow-fx-coroutines:1.2.0")
    implementation("io.coil-kt:coil-compose:2.4.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}