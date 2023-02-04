import io.gitlab.arturbosch.detekt.Detekt
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import org.jlleitschuh.gradle.ktlint.tasks.GenerateReportsTask

plugins {
    id("com.android.application") version "7.4.0" apply false
    id("com.android.library") version "7.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("io.gitlab.arturbosch.detekt") version "1.22.0-RC1"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
}

buildscript {
    val hiltVersion by extra("2.44")

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.22.0")
    }
} // Top-level build file where you can add configuration options common to all sub-projects/modules.

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

// Tasks for providing dir for generated reports from detekt and ktlint
tasks.withType<GenerateReportsTask> {
    reportsOutputDirectory.set(
        File(project.buildDir, "reports/ktlint/merged-ktlint-results.xml")
    )
}

// Tasks for automatically installing git-hooks and making it executable also.
tasks.register("installGitHook", Copy::class) {
    from(file("$rootDir/scripts/pre-push"))
    into(file("$rootDir/.git/hooks"))
    fileMode = 0b0111101101 // -rwxr-xr-x
}

tasks.create(name = "gitExecutableHooks") {
    doLast {
        Runtime.getRuntime().exec("chmod -R +x .git/hooks/")
    }
}
tasks.getByPath("gitExecutableHooks").dependsOn(tasks.named("installGitHook"))
tasks.getByPath(":app:clean").dependsOn(tasks.named("gitExecutableHooks"))

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
