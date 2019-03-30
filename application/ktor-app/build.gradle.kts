import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
//    application
    id("com.github.johnrengelman.shadow") version "4.0.3"
}

group = "vova.example"
version = "1.0-SNAPSHOT"

val ktor_version = "1.1.3"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":config"))
    implementation(project(":ktor-controller"))
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-jackson:$ktor_version")
    runtime("org.slf4j:slf4j-simple:1.7.26")
//    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")

}

//application {
//    mainClassName = "vova.example.vertx.RestVertxApplication"
//}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

