import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
//    application
    id("com.github.johnrengelman.shadow") version "4.0.3"
}

group = "vova.example"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":config"))
    implementation(project(":javalin-controller"))
    implementation("io.javalin:javalin:2.8.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
    runtime("org.slf4j:slf4j-simple:1.7.26")
}

//application {
//    mainClassName = "vova.example.vertx.RestVertxApplication"
//}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

