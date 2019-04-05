import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    id("org.springframework.boot") version "2.2.0.BUILD-SNAPSHOT"
    kotlin("jvm")
    kotlin("plugin.spring") version "1.3.21"
}

apply(plugin = "io.spring.dependency-management")

group = "vova.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven("https://repo.spring.io/snapshot")
    maven("https://repo.spring.io/milestone")

}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation(project(":config"))
    implementation(project(":domain"))
    implementation(project(":usecase"))
    implementation(project(":spring-webflux-controller"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}