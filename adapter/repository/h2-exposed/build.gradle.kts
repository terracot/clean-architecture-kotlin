import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") 
}

group = "vova.example"
version = "1.0-SNAPSHOT"

val h2Version = "1.4.196"
val exposedVersion = "0.12.1"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":domain"))
//    implementation("com.hazelcast:hazelcast:3.9.3")
    implementation("com.h2database:h2:$h2Version")
    implementation("org.jetbrains.exposed:exposed:$exposedVersion")
    implementation("com.zaxxer:HikariCP:3.3.1")

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

