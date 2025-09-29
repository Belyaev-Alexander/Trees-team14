plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.22" // Kotlin + JVM
    id("jacoco")
}
jacoco{
    toolVersion = "0.8.12"
}
kotlin{
    jvmToolchain(21)
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
tasks.jacocoTestReport {
    dependsOn(tasks.test) // Зависит от выполнения тестов
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }
}