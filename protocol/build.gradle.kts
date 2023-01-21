plugins {
    kotlin("jvm")
    application
    id("com.github.sessionkotlin.plugin")
}
val sessionKotlinVersion: String by project
val coroutinesVersion: String by project

dependencies {
    api("com.github.sessionkotlin:lib:$sessionKotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
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

