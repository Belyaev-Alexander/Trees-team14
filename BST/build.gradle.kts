plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.0" // Kotlin + JVM
}

repositories {
    mavenCentral() // Репозиторий для загрузки библиотек
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0") // Стандартная библиотека Kotlin

    // Тестирование (только JUnit 5):
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2") // Основная библиотека
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.0") // Kotlin-ассерты (assertTrue, assertEquals и др.)
}

tasks.test {
    useJUnitPlatform() // Активация JUnit 5
}
