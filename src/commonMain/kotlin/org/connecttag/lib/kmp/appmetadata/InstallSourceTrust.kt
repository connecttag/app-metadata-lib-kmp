package org.connecttag.lib.kmp.appmetadata

import kotlinx.serialization.Serializable

/**
 * Canonical Install Source Trust categorization.
 * Pure Kotlin Multiplatform domain model (SSOT).
 */
@Serializable
public enum class InstallSourceTrust {
    UNKNOWN,
    PLAY_STORE,
    THIRD_PARTY_STORE;

    public companion object {
        private val playInstallers: Set<String> = setOf(
            "com.android.vending",
            "com.google.android.feedback"
        )
        private val otherStoreInstallers: Set<String> = setOf(
            "com.sec.android.app.samsungapps",
            "com.huawei.appmarket",
            "com.amazon.venezia",
            "org.fdroid.fdroid",
            "org.fdroid.fdroid.privileged",
            "com.xiaomi.mipicks"
        )

        public fun fromInstaller(packageName: String?): InstallSourceTrust {
            val identity = packageName?.trim()?.lowercase() ?: return UNKNOWN
            return when (identity) {
                in playInstallers -> PLAY_STORE
                in otherStoreInstallers -> THIRD_PARTY_STORE
                else -> UNKNOWN
            }
        }
    }
}

/**
 * Backward compatibility alias for StandardInstallSourceTrust.
 */
public typealias StandardInstallSourceTrust = InstallSourceTrust
