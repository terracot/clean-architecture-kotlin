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
    implementation("org.springframework:spring-web:5.0.5.RELEASE")
    implementation("com.fasterxml.jackson.core:jackson-core:2.9.5")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.9.0")
//    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}