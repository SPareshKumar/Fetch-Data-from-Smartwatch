pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {

    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs{
        create("libs") {
            library("kotlin-stdlib", "org.jetbrains.kotlin:kotlin-stdlib:1.8.22")
            library("kotlin-stdlib-jdk7", "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.22")
            library("kotlin-stdlib-jdk8", "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.22")
            plugin("kotlin-android", "org.jetbrains.kotlin:kotlin-grade-plugin").version("1.8.22")

        }
    }
}

rootProject.name = "Health Manager"
include(":app")
