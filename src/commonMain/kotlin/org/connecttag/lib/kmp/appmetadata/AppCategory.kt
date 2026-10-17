package org.connecttag.lib.kmp.appmetadata

import kotlinx.serialization.Serializable

/**
 * Standardized App Categories across ConnectTag Suite (SoorNet, Hazm, SoorDefender).
 * Pure Kotlin Multiplatform domain model (Single Source of Truth).
 */
@Serializable
public enum class AppCategory {
    SYSTEM,
    SYSTEM_CRITICAL,
    GOOGLE_SERVICES,
    SOCIAL_MEDIA,
    MESSAGING,
    BROWSER,
    EMAIL,
    MEDIA,
    GAME,
    BANKING,
    WALLET,
    SHOPPING,
    PRODUCTIVITY,
    UTILITY,
    NAVIGATION,
    SECURITY,
    VPN,
    DEVELOPER_TOOLS,
    CALLER_ID,
    MODIFIED_APPS,
    SUSPICIOUS,
    MALWARE,
    ADWARE,
    TRACKER,
    USER_APP,
    UNKNOWN;

    public companion object {
        public fun fromOrNull(name: String?): AppCategory? {
            if (name.isNullOrBlank()) return null
            val normalized = name.trim().uppercase()
            return entries.firstOrNull { it.name == normalized } ?: when (normalized) {
                "SOCIAL" -> SOCIAL_MEDIA
                "CHAT", "COMMUNICATION" -> MESSAGING
                "INTERNET", "WEB" -> BROWSER
                "AUDIO", "VIDEO", "PHOTO", "PHOTOGRAPHY" -> MEDIA
                "FINANCE" -> BANKING
                "TOOLS", "UTILITIES" -> UTILITY
                "MAPS", "TRAVEL" -> NAVIGATION
                "DEV", "DEVELOPMENT" -> DEVELOPER_TOOLS
                else -> null
            }
        }
    }
}

/**
 * Backward compatibility alias for StandardAppCategory.
 */
public typealias StandardAppCategory = AppCategory
