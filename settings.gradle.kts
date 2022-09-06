pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()

        maven {
            name = "SessionKotlin-GithubPackages"
            url = uri("https://maven.pkg.github.com/d-costa/sessionkotlin")
        }
    }
}

rootProject.name = "sessionkotlin-template-gradle"
include("protocol")
include("app")
