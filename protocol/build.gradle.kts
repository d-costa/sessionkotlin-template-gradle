plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
    application
}

dependencies {
    api("org.david:sessionkotlin-lib:0.0.2")
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

