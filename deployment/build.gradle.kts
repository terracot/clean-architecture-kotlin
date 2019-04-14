import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

group = "vova.example"
version = "1.0-SNAPSHOT"


val kubernetesDslVersion = "2.0.1"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compile("com.fkorotkov:kubernetes-dsl:$kubernetesDslVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

