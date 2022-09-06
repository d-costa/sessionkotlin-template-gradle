import java.util.Properties
import java.io.FileInputStream

plugins {
    kotlin("jvm") version "1.6.20" apply false
    // https://github.com/d-costa/sessionkotlin/packages/1416283
    id("com.github.d-costa.sessionkotlin.plugin") version "2.0.1" apply false
}

subprojects{
    repositories {
        mavenCentral()
        maven {
            val skGithubPackagesRepo: String by project
            name = skGithubPackagesRepo
            url = uri("https://maven.pkg.github.com/d-costa/sessionkotlin")
        }
    }
}