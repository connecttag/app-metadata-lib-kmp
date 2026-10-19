plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.kotlin.serialization)
    `maven-publish`
}

group = "org.connecttag.lib"
version = "1.1.0"

kotlin {
    jvmToolchain(21)

    android {
        namespace = "org.connecttag.lib.metadata"
        compileSdk = 37
        minSdk = 24
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }
    }

    jvm {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.coroutines.core)
            api(libs.kotlinx.serialization.json)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

publishing {
    publications.withType<MavenPublication> {
        pom {
            name.set("ConnectTag App Metadata (KMP)")
            description.set("Intelligent application categorization, metadata extraction, trust evaluation, and install-source verification for Kotlin Multiplatform (Android & JVM)")
            url.set("https://github.com/connecttag/app-metadata-lib-kmp")
            licenses {
                license {
                    name.set("The Apache License, Version 2.0")
                    url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }
            developers {
                developer {
                    id.set("connecttagye")
                    name.set("Connect Tag")
                    email.set("connecttagye@gmail.com")
                }
            }
            scm {
                connection.set("scm:git:https://github.com/connecttag/app-metadata-lib-kmp.git")
                developerConnection.set("scm:git:ssh://git@github.com:connecttag/app-metadata-lib-kmp.git")
                url.set("https://github.com/connecttag/app-metadata-lib-kmp/tree/main")
            }
        }
    }
}
