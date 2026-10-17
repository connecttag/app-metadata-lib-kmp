package org.connecttag.lib.kmp.appmetadata

import kotlinx.serialization.Serializable

/**
 * Standard Trust Levels evaluated for applications.
 * Pure Kotlin Multiplatform domain model (SSOT).
 */
@Serializable
public enum class AppTrust {
    TRUSTED,
    NORMAL,
    CAUTION,
    SUSPICIOUS,
    BLOCKED;

    public companion object {
        public fun fromRiskScore(score: Int, category: AppCategory? = null): AppTrust = when {
            category == AppCategory.SYSTEM_CRITICAL ||
                category == AppCategory.SYSTEM ||
                category == AppCategory.GOOGLE_SERVICES ||
                score < 25 -> TRUSTED
            score < 50 -> NORMAL
            score < 75 -> CAUTION
            score < 90 -> SUSPICIOUS
            else -> BLOCKED
        }
    }
}

/**
 * Backward compatibility alias for StandardAppTrust.
 */
public typealias StandardAppTrust = AppTrust
