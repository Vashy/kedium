import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
    application
}

val kotestVersion = "5.3.0"
val assertkVersion = "0.25"
val jupiterVersion = "5.8.1"
val mokkVersion = "1.12.3"

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "13"
    }
}

repositories {
    mavenCentral()
}

tasks.compileKotlin {
    version = "1.13"
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.mockk:mockk:$mokkVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-params:$jupiterVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-suite:1.8.2")
}

application {
    mainClass.set("org.tgranz.kedium.AppKt")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
