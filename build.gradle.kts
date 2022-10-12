import java.util.Properties
import java.io.FileInputStream

plugins {
    kotlin("jvm") version "1.6.20" apply false
    // https://github.com/d-costa/sessionkotlin/packages/1416283
    id("com.github.d-costa.sessionkotlin.plugin") version "2.0.3" apply false
}

subprojects{
    repositories {
        maven {
            // Load GitHub credentials
            val props = Properties()
            val envFile = File(rootDir.path + "/.env")
            if (envFile.exists())
                props.load(FileInputStream(envFile))

            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/d-costa/sessionkotlin")
            credentials {
                username = props.getProperty("USERNAME") ?: System.getenv("USERNAME")
                password = props.getProperty("TOKEN") ?: System.getenv("TOKEN")
            }
        }
        mavenCentral()
    }
}