import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.mobile.services)
    alias(libs.plugins.google.firebase.crashlytics)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    
//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach { iosTarget ->
//        iosTarget.binaries.framework {
//            baseName = "ComposeApp"
//            isStatic = true
//        }
//    }
    
    jvm("desktop")


    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            // shared
            implementation(libs.torquelink.shared)

            // android core
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.core.splashscreen)
            implementation(libs.androidx.material3.window.size.android)

            // lifecycle
            implementation(libs.androidx.lifecycle.runtime.ktx)
            implementation(libs.androidx.lifecycle.compose)

            // compose
            implementation(project.dependencies.platform(libs.androidx.compose.bom))
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.ui)
            implementation(libs.androidx.ui.graphics)
            implementation(libs.androidx.ui.tooling.preview)
            implementation(libs.androidx.material3)
            implementation(libs.androidx.material.icons)

            // koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.bundles.koin.compose)

            //navigation
            implementation(libs.androidx.compose.navigation)

            // serialization
            implementation(libs.kotlinx.serialization.json)

            // datastore
            implementation(libs.androidx.datastore)

//            //Logback
            implementation(libs.logback.classic)

            //ktor
            implementation(libs.bundles.ktor)
            implementation(libs.ktor.client.android)

            // google
            implementation(libs.google.accompanist.permissions)
            implementation(project.dependencies.platform(libs.google.firebase.bom))
            implementation(libs.google.firebase.analytics)
            implementation(libs.google.firebase.crashlytics)
            implementation(libs.firebase.messaging)
            implementation(libs.play.services.ads)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            //navigation
            implementation(libs.androidx.compose.navigation)

            // serialization
            implementation(libs.kotlinx.serialization.json)

            // Date time
            implementation(libs.kotlinx.datetime)

            // shared
            implementation(libs.torquelink.shared)

            // ktor
            implementation(libs.bundles.ktor)

            // coil
            implementation(libs.bundles.coil.compose)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

configurations.all {
    exclude("jakarta.validation", "jakarta.validation-api")

    // Add this line to fix the duplicate class error
    exclude(group = "commons-logging", module = "commons-logging")
}

android {
    namespace = "nl.torquelink"
    compileSdk = 35

    defaultConfig {
        applicationId = "nl.torquelink"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

    }
    packaging {
        resources {
            merges += "/META-INF/{LICENSE-notice.md,INDEX.LIST,LGPL-3.0.txt,ASL-2.0.txt,AL2.0,LGPL2.1,DEPENDENCIES,LICENSE.md,NOTICE.md}"
            merges += "META-INF/versions/9/OSGI-INF/MANIFEST.MF"
            excludes += "draftv4/schema"
            excludes += "draftv3/schema"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

compose.desktop {
    application {
        mainClass = "nl.torquelink.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "nl.torquelink"
            packageVersion = "1.0.0"
        }
    }
}

configurations.all {
    resolutionStrategy {
        force("ch.qos.logback:logback-classic:1.3.15")
    }
}