plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)

    id("androidx.navigation.safeargs")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.traveltrip"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.traveltrip"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "CLOUD_NAME", "\"${project.properties["CLOUD_NAME"] ?: ""}\"")
        buildConfigField("String", "API_KEY", "\"${project.properties["API_KEY"] ?: ""}\"")
        buildConfigField("String", "API_SECRET", "\"${project.properties["API_SECRET"] ?: ""}\"")


        buildConfigField(
            "String",
            "AMADEUS_BASE_URL",
            "\"${project.properties["AMADEUS_BASE_URL"] ?: ""}\""
        )
        buildConfigField(
            "String",
            "AMADEUS_API_KEY",
            "\"${project.properties["AMADEUS_API_KEY"] ?: ""}\""
        )
        buildConfigField(
            "String",
            "AMADEUS_API_SECRET",
            "\"${project.properties["AMADEUS_API_SECRET"] ?: ""}\""
        )
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.androidx.room.runtime)
    implementation(libs.firebase.auth)
    implementation(libs.picasso)


    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.converter.gson)
    implementation (libs.places)



    implementation(libs.cloudinary.android)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.circleimageview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}