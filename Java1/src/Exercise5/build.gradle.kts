plugins {
    java
    application
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(18))
    }
}

application {
    mainClass.set("org.example.TheSameNumbers")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

