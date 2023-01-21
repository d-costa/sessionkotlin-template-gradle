pluginManagement {
    repositories {
        maven {
            // Load GitHub credentials
            val props = java.util.Properties()
            val envFile = File(rootDir.path + "/.env")
            if (envFile.exists())
                props.load(java.io.FileInputStream(envFile))

            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/sessionkotlin/sessionkotlin")
            credentials {
                username = props.getProperty("USERNAME") ?: System.getenv("USERNAME")
                password = props.getProperty("TOKEN") ?: System.getenv("TOKEN")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "sessionkotlin-template-gradle"
include("protocol")
include("app")
