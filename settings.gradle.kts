rootProject.name = "clean-architecture-kotlin"

include(":config")
include(":domain")
include(":deployment")
include(":usecase")
include(":jug")
include(":uuid")
include(":in-memory-db-simple")
include(":in-memory-db-hazelcast")
include(":db-h2-exposed")
include(":encoder-sha256")
include(":spring-controller")
include(":vertx-controller")
include(":javalin-controller")
include(":ktor-controller")
include(":spring-webflux-controller")

include(":manual-app")
include(":spring-app")
include(":vertx-app")
include(":javalin-app")
include(":ktor-app")
include(":spring-webflux-app")

project(":encoder-sha256").projectDir = File("adapter/encoder/sha256")
project(":jug").projectDir = File("adapter/id-generator/jug")
project(":uuid").projectDir = File("adapter/id-generator/uuid")
project(":in-memory-db-simple").projectDir = File("adapter/repository/in-memory-simple")
project(":in-memory-db-hazelcast").projectDir = File("adapter/repository/in-memory-hazelcast")
project(":db-h2-exposed").projectDir = File("adapter/repository/h2-exposed")
project(":spring-controller").projectDir =   File("adapter/controller/spring-controller")
project(":vertx-controller").projectDir =     File("adapter/controller/vertx-controller")
project(":javalin-controller").projectDir = File("adapter/controller/javalin-controller")
project(":ktor-controller").projectDir =       File("adapter/controller/ktor-controller")
project(":spring-webflux-controller").projectDir =   File("adapter/controller/spring-webflux-controller")

project(":manual-app").projectDir = File("application/manual-app")
project(":spring-app").projectDir = File("application/spring-app")
project(":vertx-app").projectDir = File("application/vertx-app")
project(":javalin-app").projectDir = File("application/javalin-app")
project(":ktor-app").projectDir = File("application/ktor-app")
project(":spring-webflux-app").projectDir = File("application/spring-webflux-app")

pluginManagement {
    repositories {
        maven("https://repo.spring.io/snapshot")
        maven("https://repo.spring.io/milestone")
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.springframework.boot") {
                useModule("org.springframework.boot:spring-boot-gradle-plugin:${requested.version}")
            }
        }
    }
}