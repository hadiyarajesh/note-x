import io.gitlab.arturbosch.detekt.Detekt
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import org.jlleitschuh.gradle.ktlint.tasks.GenerateReportsTask

buildscript {
    val hiltVersion by extra("2.42")

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.22.0-RC1")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.2.2" apply false
    id("com.android.library") version "7.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.7.0" apply false
    id("io.gitlab.arturbosch.detekt") version "1.22.0-RC1"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    configureDetekt()
    configureKtLint()
}

configureDetekt()
fun Project.configureDetekt() {
    tasks.withType<Detekt> {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        setSource(files(projectDir))
        exclude("**/build/**")
        parallel = true
        baseline.set(file(path = "${rootProject.projectDir}/config/detekt/detekt-baseline.xml"))
        autoCorrect = true
        reports {
            xml.required.set(true)
            html.required.set(false)
            txt.required.set(false)
            sarif.required.set(false)
        }
    }
}

fun Project.configureKtLint() {
    this.configure<KtlintExtension> {
        version.set("0.45.2")
        android.set(true)
        outputToConsole.set(true)
        ignoreFailures.set(true)
        enableExperimentalRules.set(true)
        disabledRules.set(
            setOf(
                "unused-imports",
                "final-newline",
                "max-line-length",
                "experimental:argument-list-wrapping",
                "no-wildcard-imports",
                "experimental:trailing-comma",
                "experimental:comment-wrapping"
            )
        )
        reporters {
            reporter(ReporterType.CHECKSTYLE)
        }
        filter {
            exclude("**/generated/**")
            include("**/build/**")
        }
    }
}

//val deletePreviousGitHOok by tasks.registering(Delete::class) {
//    group = "utils"
//    description = "Deleting previous githook"
//
////    val preCommit = "${rootProject.rootDir}/.git/hooks/pre-commit"
//    val prePush = "${rootProject.rootDir}/.git/hooks/pre-push"
//    if (file(prePush).exists()) {
//        delete(prePush)
//    }
//}
//
//val installGitHook by tasks.registering(Copy::class) {
//    group = "utils"
//    description = "Adding githook to local working copy, this must be run manually"
//
//    dependsOn(deletePreviousGitHOok)
//    from("${rootProject.rootDir}/.githooks/pre-push")
//    into("${rootProject.rootDir}/.git/hooks")
//    fileMode = 0b0111101101
//}

tasks.register("installGitHook", Copy::class) {
    from(file("$rootDir/.githooks"))
    into(file("$rootDir/.git/hooks"))
    fileMode = 0b0111101101 // -rwxr-xr-x
}
tasks.getByPath(":app:preBuild").dependsOn(tasks.named("installGitHook"))

tasks.withType<GenerateReportsTask> {
    reportsOutputDirectory.set(
        File(project.buildDir, "reports/ktlint/merged-ktlint-results.xml")
    )
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}


