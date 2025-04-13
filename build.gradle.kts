plugins {
    `java-library`
    `maven-publish`
    war
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation(libs.org.eclipse.persistence.org.eclipse.persistence.jpa)
    implementation(libs.org.postgresql.postgresql)
    implementation(libs.org.primefaces.primefaces)
    implementation(libs.ch.qos.logback.logback.classic)
    implementation(libs.jakarta.platform.jakarta.jakartaee.web.api)
    implementation(libs.org.projectlombok.lombok)

    annotationProcessor(libs.org.projectlombok.lombok)

    testImplementation(libs.org.junit.jupiter.junit.jupiter.api)
    testImplementation(libs.org.junit.jupiter.junit.jupiter.engine)
}

group = "com.arekalov.jsfgraph"
version = "1.0-SNAPSHOT"
description = "interactive-graph-ui"
java.sourceCompatibility = JavaVersion.VERSION_17

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
