plugins {
    java
    id("org.springframework.boot") version "3.2.5"
}

group = "portfolio"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(18))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.5")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.5")
}

tasks.test {
    useJUnitPlatform()
}
