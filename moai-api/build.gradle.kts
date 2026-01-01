plugins {
    `java-library`
    idea
    id("com.gradleup.shadow") version "9.3.0"  // Última versión mantenida (2026)
}

group = "io.canvasmc"
version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.canvasmc.io/snapshots")  // Si Canvas tiene repo propio
}

dependencies {
    // Bump DivineMC-style: versiones seguras y modernas
    api("org.jetbrains:annotations:26.0.2-1")          // Última con fixes
    implementation("org.yaml:snakeyaml:2.5")           // Secure & performant
    api("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")

    // Log4j2 completo (API + CORE) – usar versiones alineadas (alineado con divinemc-server)
    implementation("org.apache.logging.log4j:log4j-api:2.24.1")
    implementation("org.apache.logging.log4j:log4j-core:2.24.1")
    // Nota: no usamos procesador de anotaciones de Log4j en este módulo porque no hay @Plugin propios.
    // El transformador de Shadow seguirá manejando el cache de plugins si fuese necesario.

    // Opcional: Sentry (de Pufferfish/DivineMC) para reporte automático de crashes
    implementation("io.sentry:sentry:8.0.0")  // Descomenta si quieres monitoreo

    // Opcional: commons-lang3 si lo necesitas (DivineMC lo agrega para Bukkit compat)
    compileOnly("org.apache.commons:commons-lang3:3.19.0")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(21)
        options.compilerArgs.add("--enable-preview")

        // DivineMC-style: soporte SIMD + hide warnings
        options.compilerArgs.add("--add-modules=jdk.incubator.vector")
        options.compilerArgs.add("-Xlint:-module")
        options.compilerArgs.add("-Xlint:-removal")
        options.compilerArgs.add("-Xlint:-dep-ann")
    }

    javadoc {
        options.encoding = "UTF-8"
        // Hide unnecessary warnings (como DivineMC)
        (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:none", "-quiet")
        (options as StandardJavadocDocletOptions).addStringOption("-add-modules", "jdk.incubator.vector")
    }

    processResources {
        filteringCharset = "UTF-8"
    }

    shadowJar {
        archiveClassifier.set("all")
        archiveFileName.set("moai-${version}-all.jar")

        manifest {
            attributes["Main-Class"] = "io.canvasmc.moai.MoaiInitializer"
        }

        // Crítico para Log4j2 y otros SPI
        mergeServiceFiles()

        // Ensures Log4j2's plugin registry is generated into the shaded JAR
        transform(com.github.jengelman.gradle.plugins.shadow.transformers.Log4j2PluginsCacheFileTransformer::class.java)
        // Do NOT relocate Log4j packages. Relocation breaks Log4j2's plugin discovery
        // and causes pattern converters (e.g., %d, %level) to be unrecognized at runtime.

        // Limpieza META-INF
        exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
        exclude("META-INF/maven/**", "META-INF/LICENSE", "META-INF/NOTICE")
    }

    build {
        dependsOn(shadowJar)
    }
}
