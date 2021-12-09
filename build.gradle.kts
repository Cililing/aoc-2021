plugins {
    kotlin("jvm") version "1.6.0"
}

group "com.cililing"
version "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.3"
    }
}