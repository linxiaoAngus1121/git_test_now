
plugins {
    id("com.android.application")
    id( "kotlin-android")
}
android {
    compileSdk=31

    defaultConfig {
        applicationId="com.example.gittest"
        minSdk=21
        targetSdk=31
        versionCode=1
        versionName="1.0"
        testInstrumentationRunner="androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("float","testFloat","10.2f")

        manifestPlaceholders["value"]=10
    }

    android.applicationVariants.all {
            val name = buildType.name
        println("outputType111${name}")
        if(name=="release"){
            val variant = this
            outputs.all {
            }
        }

    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled=false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility=JavaVersion.VERSION_1_8
        targetCompatibility=JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.3.2")

    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
}

fun releaTime() {
}
