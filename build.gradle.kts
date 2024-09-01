
plugins {
    // No need to declare plugins here if you are using the version catalog in module-level files
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("printDependencies") {
    doLast {
        println("Kotlin Stdlib: ${project.extensions.findByType<VersionCatalogsExtension>()?.named("libs")?.findLibrary("kotlin-stdlib")?.get()?.toString()}")
    }
}
