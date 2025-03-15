import org.gradle.process.internal.ExecException
import java.io.ByteArrayOutputStream

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

val lab3TasksGroup = "lab3"

tasks.register("compile") {
    group = lab3TasksGroup
    dependsOn("compileJava")
}

tasks.register<Jar>("customJar") {
    group = lab3TasksGroup
    from(sourceSets.main.get().output)

    manifest {
        attributes(
            "Implementation-Title" to "My Application",
            "Implementation-Version" to project.version,
            "Main-Class" to "com.arekalov.jsfgrap.CoordinateHandlerBean"
        )
    }

    archiveBaseName.set("my-application")
    archiveVersion.set(project.version.toString())
    archiveClassifier.set("custom")
}

tasks.named("build") {
    group = lab3TasksGroup
    dependsOn("customJar")
}

tasks.war {
    group = lab3TasksGroup
    from(tasks.named("customJar").map { it.outputs.files }) {
        into("WEB-INF/lib")
    }
}

tasks.clean.configure {
    group = lab3TasksGroup
}

tasks.test {
    group = lab3TasksGroup
    useJUnitPlatform()
}

tasks.register("music") {
    group = lab3TasksGroup
    dependsOn(tasks.build)
    doLast {
        println("HOY!")
    }
}

tasks.register("history") {
    group = lab3TasksGroup

    doLast {
        try {
            println("Running build task...")
            exec {
                commandLine("./gradlew", "build")
            }

            println("Build was successful or already up to date. No rollback needed.")
        } catch (e: ExecException) {
            println("Build failed. Attempting to rollback...")
            rollbackAndBuild()
        }
    }
}

fun rollbackAndBuild() {
    val commits = gitCommand("rev-list HEAD")?.lines() ?: return
    var lastSuccessfulCommit: String? = null
    val currentlyCheckedOutCommit = gitCommand("rev-parse HEAD")!!.trim()

    for (commit in commits) {
        gitCommand("checkout $commit")
        if (tryBuild()) {
            lastSuccessfulCommit = commit
            println("Successful build at commit: $commit")
            break
        } else {
            println("Failed build at commit: $commit")
        }
    }

    if (lastSuccessfulCommit == null) {
        println("No successful build found.")
        val earliestCommit = commits.last()
        val nextCommit = commits[commits.indexOf(earliestCommit) - 1]
        val diff = gitCommand("diff $earliestCommit $nextCommit")
        val diffFile = project.file("build/failed_build_diff.txt")
        diffFile.writeText(diff ?: "No diff available.")
        println("Diff between $earliestCommit and $nextCommit has been saved to ${diffFile.path}")
    }

    gitCommand("checkout $currentlyCheckedOutCommit")
}

private fun gitCommand(command: String): String? {
    val output = ByteArrayOutputStream()
    try {
        exec {
            commandLine("git", *command.split(" ").toTypedArray())
            standardOutput = output
        }
    } catch (e: Exception) {
        logger.error("Failed to execute git command: $command", e)
        return null
    }
    return output.toString()
}

private fun tryBuild(): Boolean {
    return try {
        exec {
            commandLine("./gradlew", "clean", "build")
        }
        true
    } catch (e: ExecException) {
        false
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
