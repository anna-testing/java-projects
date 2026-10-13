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
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.5")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("org.springframework.boot:spring-boot-starter-security:3.2.5")
    // JWT dependencies
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.5")
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
    options.compilerArgs.addAll(listOf("-Xlint:all,-serial,-path,-deprecation"))
}

tasks.test {
    useJUnitPlatform()
}
