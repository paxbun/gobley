import gobley.gradle.GobleyHost
import gobley.gradle.cargo.dsl.jvm
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    id("dev.gobley.cargo")
}

cargo {
    builds.jvm {
        embedRustLibrary = rustTarget == GobleyHost.current.rustTarget
    }
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
    sourceSets {
        main {
            dependencies {
                implementation(libs.jna)
            }
        }
        test {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotest.assertions.core)
            }
        }
    }
}
