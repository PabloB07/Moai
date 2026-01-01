import java.util.Locale

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        maven("https://central.sonatype.com/repository/maven-snapshots/")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

if (!file(".git").exists()) {
    val errorText = """
        
        =====================[ ERROR ]=====================
         The Moai project directory is not a properly cloned Git repository.
         
         In order to build Moai from source you must clone
         the Moai repository using Git, not download a code
         zip from GitHub.  
         See https://github.com/deivaxxx/Moai
         for further information on building and modifying Moai.
        ===================================================
    """.trimIndent()
    error(errorText)
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "canvas"
for (name in listOf("canvas-api", "canvas-server", "canvas-test-plugin")) {
    val projName = name.lowercase(Locale.ENGLISH)
    include(projName)
    findProject(":$projName")!!.projectDir = file(name)
}
