package org.connecttag.lib.kmp.appmetadata

/**
 * Intelligent pure Kotlin application category detector (SSOT).
 * Evaluates package name, application label, system status, and UID to determine [AppCategory].
 */
public object AppCategoryDetector {

    public fun detect(
        packageName: String,
        appLabel: String? = null,
        isSystem: Boolean = false,
        uid: Int = 10000
    ): AppCategory {
        val pkg = packageName.trim().lowercase()
        val label = appLabel?.trim()?.lowercase().orEmpty()

        if (pkg.isBlank() || uid < 0) return AppCategory.UNKNOWN

        // 1. Critical System Applications
        if (isSystemCritical(pkg, uid)) {
            return AppCategory.SYSTEM_CRITICAL
        }

        // 2. Google Services & OS Framework Apps
        if (pkg.startsWith("com.google.") || pkg.startsWith("com.android.")) {
            if (pkg.contains("gms") || pkg.contains("gsf")) return AppCategory.GOOGLE_SERVICES
            if (isBrowserApp(pkg, label)) return AppCategory.BROWSER
            if (isEmailApp(pkg, label)) return AppCategory.EMAIL
            if (isMediaApp(pkg, label)) return AppCategory.MEDIA
            if (isNavigationApp(pkg, label)) return AppCategory.NAVIGATION
            if (isMessagingApp(pkg, label)) return AppCategory.MESSAGING
            return AppCategory.GOOGLE_SERVICES
        }

        // 3. Modified Apps (WhatsApp Gold, GBWhatsApp, Instander, etc.)
        if (isModifiedApp(pkg, label)) {
            return AppCategory.MODIFIED_APPS
        }

        // 4. Caller ID / Number Book Apps
        if (isCallerIdApp(pkg, label)) {
            return AppCategory.CALLER_ID
        }

        // 5. Financial & Banking & Wallet
        val exactFinancial = AppClassificationCatalog.getFinancialCategory(pkg)
        if (exactFinancial != null) {
            return exactFinancial
        }
        if (isFinancialApp(pkg, label)) {
            return if (label.contains("wallet") || label.contains("محفظة") || pkg.contains("wallet")) {
                AppCategory.WALLET
            } else {
                AppCategory.BANKING
            }
        }

        // 6. Social Media & Messaging
        // Exact catalog identities take precedence over broad package/label keywords (e.g. Facebook Messenger).
        if (pkg in AppClassificationCatalog.MESSAGING_PACKAGES) return AppCategory.MESSAGING
        if (pkg in AppClassificationCatalog.SOCIAL_MEDIA_PACKAGES) return AppCategory.SOCIAL_MEDIA
        if (isSocialMediaApp(pkg, label)) return AppCategory.SOCIAL_MEDIA
        if (isMessagingApp(pkg, label)) return AppCategory.MESSAGING

        // 7. Browsers
        if (isBrowserApp(pkg, label)) return AppCategory.BROWSER

        // 8. Email
        if (isEmailApp(pkg, label)) return AppCategory.EMAIL

        // 9. Navigation & Maps
        if (isNavigationApp(pkg, label)) return AppCategory.NAVIGATION

        // 10. Games
        if (isGameApp(pkg, label)) return AppCategory.GAME

        // 11. Media & Streaming
        if (isMediaApp(pkg, label)) return AppCategory.MEDIA

        // 12. Shopping
        if (isShoppingApp(pkg, label)) return AppCategory.SHOPPING

        // 13. VPN & Security
        if (isVpnApp(pkg, label)) return AppCategory.VPN
        if (isSecurityApp(pkg, label)) return AppCategory.SECURITY

        // 14. Developer Tools
        if (isDeveloperApp(pkg, label)) return AppCategory.DEVELOPER_TOOLS

        // 15. Known suspicious / blacklisted
        if (AppClassificationCatalog.DEFAULT_BLACKLISTED_APPS.contains(pkg)) {
            return AppCategory.SUSPICIOUS
        }

        // 16. Generic System App
        if (isSystem || isSystemPackage(pkg)) {
            return AppCategory.SYSTEM
        }

        return AppCategory.USER_APP
    }

    public fun isSystemCritical(packageName: String, uid: Int): Boolean {
        if (uid % 100000 < 10000) {
            val lower = packageName.lowercase()
            return lower == "android" ||
                lower.startsWith("com.android.phone") ||
                lower.startsWith("com.android.server.telecom") ||
                lower.startsWith("com.android.systemui") ||
                lower in AppClassificationCatalog.CRITICAL_SYSTEM_PACKAGES
        }
        return packageName.lowercase() in AppClassificationCatalog.CRITICAL_SYSTEM_PACKAGES
    }

    public fun isSystemPackage(packageName: String): Boolean {
        val lower = packageName.lowercase()
        return AppClassificationCatalog.SYSTEM_PACKAGE_PREFIXES.any { lower.startsWith(it) }
    }

    public fun isMessagingApp(packageName: String, appLabel: String? = null): Boolean {
        val pkg = packageName.lowercase()
        if (AppClassificationCatalog.MESSAGING_PACKAGES.contains(pkg)) return true
        if (pkg.contains("com.whatsapp") || pkg.contains("org.telegram") || pkg.contains("org.thoughtcrime.securesms")) return true

        val label = appLabel?.lowercase().orEmpty()
        return AppClassificationCatalog.MESSAGING_KEYWORDS.any {
            (label.isNotEmpty() && label.contains(it)) || pkg.contains(it)
        }
    }

    public fun isSocialMediaApp(packageName: String, appLabel: String? = null): Boolean {
        val pkg = packageName.lowercase()
        if (AppClassificationCatalog.SOCIAL_MEDIA_PACKAGES.contains(pkg)) return true

        val label = appLabel?.lowercase().orEmpty()
        return AppClassificationCatalog.SOCIAL_MEDIA_KEYWORDS.any {
            (label.isNotEmpty() && label.contains(it)) || pkg.contains(it)
        }
    }

    public fun isBrowserApp(packageName: String, appLabel: String? = null): Boolean {
        val pkg = packageName.lowercase()
        if (AppClassificationCatalog.BROWSER_PACKAGES.contains(pkg)) return true

        val label = appLabel?.lowercase().orEmpty()
        return AppClassificationCatalog.BROWSER_KEYWORDS.any {
            (label.isNotEmpty() && label.contains(it)) || pkg.contains(it)
        }
    }

    public fun isEmailApp(packageName: String, appLabel: String? = null): Boolean {
        val pkg = packageName.lowercase()
        if (AppClassificationCatalog.EMAIL_PACKAGES.contains(pkg)) return true

        val label = appLabel?.lowercase().orEmpty()
        return AppClassificationCatalog.EMAIL_KEYWORDS.any {
            (label.isNotEmpty() && label.contains(it)) || pkg.contains(it)
        }
    }

    public fun isMediaApp(packageName: String, appLabel: String? = null): Boolean {
        val pkg = packageName.lowercase()
        if (AppClassificationCatalog.MEDIA_PACKAGES.contains(pkg)) return true

        val label = appLabel?.lowercase().orEmpty()
        return AppClassificationCatalog.MEDIA_KEYWORDS.any {
            (label.isNotEmpty() && label.contains(it)) || pkg.contains(it)
        }
    }

    public fun isGameApp(packageName: String, appLabel: String? = null, category: Int? = null): Boolean {
        if (category == 0) return true // Android ApplicationInfo.CATEGORY_GAME
        val pkg = packageName.lowercase()
        if (AppClassificationCatalog.GAME_PACKAGE_PREFIXES.any { pkg.startsWith(it) }) return true

        val label = appLabel?.lowercase().orEmpty()
        return AppClassificationCatalog.GAME_KEYWORDS.any {
            (label.isNotEmpty() && label.contains(it)) || pkg.contains(it)
        }
    }

    public fun isNavigationApp(packageName: String, appLabel: String? = null): Boolean {
        val pkg = packageName.lowercase()
        if (AppClassificationCatalog.NAVIGATION_PACKAGES.contains(pkg)) return true

        val label = appLabel?.lowercase().orEmpty()
        return AppClassificationCatalog.NAVIGATION_KEYWORDS.any {
            (label.isNotEmpty() && label.contains(it)) || pkg.contains(it)
        }
    }

    public fun isFinancialApp(packageName: String, appLabel: String? = null): Boolean {
        val pkg = packageName.lowercase()
        if (AppClassificationCatalog.isKnownFinancialApp(pkg)) return true
        val label = appLabel?.lowercase().orEmpty()
        return AppClassificationCatalog.FINANCIAL_KEYWORDS.any {
            (label.isNotEmpty() && label.contains(it)) || pkg.contains(it)
        }
    }

    public fun isCallerIdApp(packageName: String, appLabel: String? = null): Boolean {
        val pkg = packageName.lowercase()
        val label = appLabel?.lowercase().orEmpty()
        return AppClassificationCatalog.CALLER_ID_KEYWORDS.any {
            (label.isNotEmpty() && label.contains(it)) || pkg.contains(it)
        }
    }

    public fun isModifiedApp(packageName: String, appLabel: String? = null): Boolean {
        val pkg = packageName.lowercase()
        val label = appLabel?.lowercase().orEmpty()

        if (AppClassificationCatalog.MODIFIED_APP_KEYWORDS.any { pkg.contains(it) || (label.isNotEmpty() && label.contains(it)) }) {
            return true
        }

        // Token-based matching to prevent false positives (e.g. "insta" inside official Instagram)
        val nameTokens = label.split(Regex("[^\\p{L}\\p{N}]+")).filter { it.isNotBlank() }
        for ((original, patterns) in AppClassificationCatalog.MODIFIED_APP_PATTERNS) {
            if (pkg.contains(original) || label.contains(original)) {
                if (patterns.any { pattern -> pattern in nameTokens || pkg.contains(pattern) }) {
                    return true
                }
            }
        }

        return false
    }

    public fun isVpnApp(packageName: String, appLabel: String? = null): Boolean {
        val pkg = packageName.lowercase()
        val label = appLabel?.lowercase().orEmpty()
        return AppClassificationCatalog.VPN_KEYWORDS.any {
            (label.isNotEmpty() && label.contains(it)) || pkg.contains(it)
        }
    }

    public fun isSecurityApp(packageName: String, appLabel: String? = null): Boolean {
        val pkg = packageName.lowercase()
        val label = appLabel?.lowercase().orEmpty()
        return AppClassificationCatalog.SECURITY_KEYWORDS.any {
            (label.isNotEmpty() && label.contains(it)) || pkg.contains(it)
        }
    }

    public fun isShoppingApp(packageName: String, appLabel: String? = null): Boolean {
        val pkg = packageName.lowercase()
        val label = appLabel?.lowercase().orEmpty()
        val shoppingKeywords = listOf("shopping", "amazon", "aliexpress", "noon", "shein", "ebay", "تسوق", "متجر")
        return shoppingKeywords.any {
            (label.isNotEmpty() && label.contains(it)) || pkg.contains(it)
        }
    }

    public fun isDeveloperApp(packageName: String, appLabel: String? = null): Boolean {
        val pkg = packageName.lowercase()
        val label = appLabel?.lowercase().orEmpty()
        val devKeywords = listOf("termux", "android.devtools", "apktool", "decompiler", "matlog", "wireshark")
        return devKeywords.any {
            (label.isNotEmpty() && label.contains(it)) || pkg.contains(it)
        }
    }
}
