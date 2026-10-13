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
    mainClass = "org.example.FinderFibonacci"
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
