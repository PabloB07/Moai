plugins {
    `java-library`
    idea
}

group = "io.canvasmc.moai"
version = "1.0.0"

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
    implementation(projects.moaiApi)
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release = 21
        options.compilerArgs.add("--enable-preview")
    }

    jar {
        archiveFileName = "Moai-Core-${project.version}.jar"
        manifest {
            attributes(
                "Main-Class" to "io.canvasmc.moai.MoaiCorePlugin"
            )
        }
    }
}

tasks.register<Copy>("buildAndCopyPlugin") {
    dependsOn(tasks.named("build"))

    from(layout.buildDirectory.dir("libs"))
    include("Moai-test-plugin-*.jar")
    include("Moai-Core-*.jar")
    into(file("../local/plugins/"))
}
