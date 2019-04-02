import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    application
    id("com.github.johnrengelman.shadow") version "4.0.3"
}

group = "vova.example"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":config"))
    implementation(project(":vertx-controller"))
    implementation("io.vertx:vertx-web:3.7.0")
    implementation("io.vertx:vertx-lang-kotlin-coroutines:3.7.0")
    implementation("io.vertx:vertx-lang-kotlin:3.7.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.1.1")
}

application {
    mainClassName = "vova.example.vertx.RestVertxApplication"
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

