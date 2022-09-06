plugins {
    kotlin("jvm")
    application
    id("com.github.d-costa.sessionkotlin.plugin")
}

val coroutinesVersion: String by project

dependencies {
    implementation(project(":protocol"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
}

application {
    // Define the main class for the application.
    mainClass.set("app.AppKt")
}
