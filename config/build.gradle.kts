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
    implementation(project(":in-memory-db-simple"))
    implementation(project(":db-h2-exposed"))
    implementation(project(":in-memory-db-hazelcast"))
    implementation(project(":jug"))
    implementation(project(":uuid"))
    implementation(project(":encoder-sha256"))
    implementation(project(":vertx-controller"))
    implementation(project(":javalin-controller"))
    implementation(project(":ktor-controller"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}