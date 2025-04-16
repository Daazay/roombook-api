
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
}

group = "org.daazay"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

ktor {
    fatJar {
        archiveFileName.set("app.jar")
    }
}

dependencies {
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.koin)
    implementation(libs.ktor.koin.logger)
    implementation(libs.ktor.server.sessions)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.auth)
    implementation(libs.postgresql)
    implementation(libs.h2)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.datetime)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.netty)
    implementation(libs.commons.codec)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}
