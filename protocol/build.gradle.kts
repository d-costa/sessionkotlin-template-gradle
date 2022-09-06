plugins {
    kotlin("jvm")
    application
    id("com.github.d-costa.sessionkotlin.plugin")
}
val sessionKotlinVersion: String by project
val coroutinesVersion: String by project

dependencies {
    api("com.github.d-costa:sessionkotlin-lib:$sessionKotlinVersion")
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

