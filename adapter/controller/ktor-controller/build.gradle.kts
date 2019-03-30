import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

group = "vova.example"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":usecase"))
    implementation(project(":domain"))
    implementation("io.ktor:ktor-server-core:1.1.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.5")


}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}