plugins {
    `java-library`
    idea
    id("org.gradle.java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "io.canvasmc"
version = "1.0.0-moai"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    // JetBrains annotations
    api("org.jetbrains:annotations:26.0.2")
    
    // SnakeYAML for YAML processing
    implementation("org.yaml:snakeyaml:2.5")
    
    
    // Paper/Spigot API
    api("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")

    // Log4j dependencies
    implementation("org.apache.logging.log4j:log4j-core:2.23.1")
    implementation("org.apache.logging.log4j:log4j-api:2.23.1")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.23.1")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release = 21
        options.compilerArgs.add("--enable-preview")
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }

    jar {
        manifest {
            attributes(
                "Main-Class" to "io.canvasmc.moai.MoaiInitializer"
            )
        }
    }

    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveClassifier.set("all")
        manifest {
            attributes(
                "Main-Class" to "io.canvasmc.moai.MoaiInitializer"
            )
        }
        from(sourceSets.main.get().output)
        configurations = listOf(project.configurations.runtimeClasspath.get())
        exclude("META-INF/maven/**")
        exclude("META-INF/LICENSE")
        exclude("META-INF/NOTICE")
    }

    register<Jar>("fatJar") {
        archiveBaseName.set("moai-api")
        archiveClassifier.set("")
        manifest {
            attributes(
                "Main-Class" to "io.canvasmc.moai.MoaiInitializer"
            )
        }
        from(sourceSets.main.get().output)
        dependsOn(configurations.runtimeClasspath)
        from({
            configurations.runtimeClasspath.get().filter { it.exists() }.map { zipTree(it) }
        })
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        include("**/*.class", "**/*.yaml", "META-INF/services/org.apache.logging.log4j.spi.Provider")
    }
}
