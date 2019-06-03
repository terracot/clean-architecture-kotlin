import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

group = "vova.example"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation(project(":usecase"))
    implementation(project(":domain"))
    implementation("org.springframework:spring-context:5.2.0.BUILD-SNAPSHOT")
    implementation("org.springframework:spring-web:5.2.0.BUILD-SNAPSHOT")
    implementation("org.springframework:spring-webflux:5.2.0.BUILD-SNAPSHOT")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.9.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}