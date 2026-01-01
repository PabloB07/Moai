plugins {
    `java-library`
    idea
}

dependencies {
    implementation(projects.foliaServer)
    implementation(projects.pufferfishApi)
}

tasks.register<Copy>("buildAndCopyPlugin") {
    dependsOn(tasks.named("build"))

    from(layout.buildDirectory.dir("libs"))
    include("NVFolia-test-plugin-*.jar")
    include("Moai-Core-*.jar")
    into(file("../local/plugins/"))
}
