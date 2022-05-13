plugins {
    kotlin("jvm")
    application
    id("com.github.d-costa.sessionkotlin.plugin") version "0.1"
}

dependencies {
    api("com.github.d-costa:sessionkotlin-lib:0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
}

application {
    // Define the main class for the application.
    mainClass.set("app.AppKt")
}

kotlin.sourceSets.main {
    kotlin.srcDirs(
        file("$buildDir/generated/sessionkotlin/main/kotlin"),
    )
}

