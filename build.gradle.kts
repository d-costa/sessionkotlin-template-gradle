import java.util.Properties
import java.io.FileInputStream

plugins {
    kotlin("jvm") version "1.6.20" apply false
}

subprojects{
    repositories {
        mavenCentral()
        maven {
            val skGithubPackagesRepo: String by project

            // Load GitHub credentials
            val props = Properties()
            val envFile = File(rootDir.path + "/.env")
            if (envFile.exists())
                props.load(FileInputStream(envFile))

            name = skGithubPackagesRepo
            url = uri("https://maven.pkg.github.com/d-costa/sessionkotlin")
            credentials {
                username = props.getProperty("USERNAME") ?: System.getenv("USERNAME")
                password = props.getProperty("TOKEN") ?: System.getenv("TOKEN")
            }
        }
    }
}