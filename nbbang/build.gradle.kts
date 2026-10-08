plugins {
    val springBootVersion = "3.2.5"
    val kotlinVersion = "1.9.23"

    id("org.springframework.boot") version springBootVersion apply false
    id("io.spring.dependency-management") version "1.1.5" apply false
    kotlin("jvm") version kotlinVersion apply false
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false
}


allprojects {
    group = "com.nbbang"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

