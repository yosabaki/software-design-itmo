plugins {
    kotlin("jvm") version "1.4.30"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.typesafe.akka:akka-actor-typed_2.13:2.6.13")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("com.beust:klaxon:5.5")
    testImplementation("junit:junit:4.12")
    testImplementation("com.typesafe.akka:akka-actor-testkit-typed_2.13:2.6.13")
    implementation(kotlin("stdlib"))
}
