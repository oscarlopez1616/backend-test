plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.10"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
}

application {
    mainClass.set("com.wallapop.marsrover.entrypoint.cli.App")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

java.sourceSets["main"].java {
    srcDir(
        "src/main/kotlin"
    )
}

tasks.test {
    useJUnit()
}

val run by tasks.getting(JavaExec::class) {
    standardInput = System.`in`
}