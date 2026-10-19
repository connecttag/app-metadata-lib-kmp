package org.connecttag.lib.kmp.appmetadata

/**
 * Single Source of Truth for App Classification Catalogs, Famous Packages,
 * Keywords, and Category Patterns across ConnectTag Suite (SoorNet, Hazm, SoorDefender).
 * Pure Kotlin Multiplatform (Zero platform dependencies).
 */
public object AppClassificationCatalog {

    // ==========================================
    // Messaging Apps
    // ==========================================
    public val MESSAGING_PACKAGES: Set<String> = setOf(
        "com.whatsapp",
        "com.whatsapp.w4b",
        "org.telegram.messenger",
        "org.thunderdog.challegram",
        "org.telegram.plus",
        "org.thoughtcrime.securesms",
        "com.facebook.orca",
        "com.facebook.mlite",
        "com.discord",
        "com.Slack",
        "com.skype.raider",
        "com.snapchat.android",
        "com.viber.voip",
        "com.tencent.mm",
        "com.threema.app",
        "im.vector.app",
        "org.matrix.android",
        "com.google.android.apps.messaging",
        "com.samsung.android.messaging"
    )

    public val MESSAGING_KEYWORDS: List<String> = listOf(
        "messaging", "messenger", "chat", "مراسلة", "دردشة", "واتساب", "تيليجرام", "تليجرام", "واتس"
    )

    // ==========================================
    // Social Media Apps
    // ==========================================
    public val SOCIAL_MEDIA_PACKAGES: Set<String> = setOf(
        "com.facebook.katana",
        "com.facebook.lite",
        "com.instagram.android",
        "com.instagram.lite",
        "com.instagram.barcelona", // Threads
        "com.twitter.android",
        "com.twitter.android.lite",
        "com.linkedin.android",
        "com.pinterest",
        "com.zhiliaoapp.musically",
        "com.ss.android.ugc.trill", // TikTok
        "com.reddit.frontpage",
        "com.snapchat.android",
        "com.tumblr"
    )

    public val SOCIAL_MEDIA_KEYWORDS: List<String> = listOf(
        "facebook", "instagram", "twitter", "tiktok", "linkedin", "social", "تواصل", "سوشيال"
    )

    // ==========================================
    // Browsers
    // ==========================================
    public val BROWSER_PACKAGES: Set<String> = setOf(
        "com.android.chrome",
        "org.mozilla.firefox",
        "org.mozilla.firefox_beta",
        "org.mozilla.focus",
        "com.microsoft.emmx", // Edge
        "com.brave.browser",
        "com.opera.browser",
        "com.opera.mini.native",
        "com.opera.touch",
        "com.sec.android.app.sbrowser", // Samsung Internet
        "com.duckduckgo.mobile.android",
        "com.uc.browser.en",
        "com.UCMobile.intl",
        "com.vivaldi.browser",
        "com.kiwibrowser.browser",
        "org.torproject.torbrowser"
    )

    public val BROWSER_KEYWORDS: List<String> = listOf(
        "browser", "متصفح", "firefox", "chrome", "opera", "brave", "edge"
    )

    // ==========================================
    // Email Apps
    // ==========================================
    public val EMAIL_PACKAGES: Set<String> = setOf(
        "com.google.android.gm",
        "com.microsoft.office.outlook",
        "com.yahoo.mobile.client.android.mail",
        "ch.protonmail.android",
        "net.thunderbird.android",
        "com.fsck.k9",
        "com.trtf.blue",
        "com.easilydo.mail"
    )

    public val EMAIL_KEYWORDS: List<String> = listOf(
        "email", "mail", "بريد", "إيميل", "outlook", "gmail"
    )

    // ==========================================
    // Media & Streaming Apps
    // ==========================================
    public val MEDIA_PACKAGES: Set<String> = setOf(
        "com.google.android.youtube",
        "com.google.android.apps.youtube.music",
        "com.google.android.apps.youtube.kids",
        "com.spotify.music",
        "com.spotify.lite",
        "com.netflix.mediaclient",
        "com.soundcloud.android",
        "tv.twitch.android.app",
        "com.anghami",
        "deezer.android.app",
        "org.videolan.vlc",
        "com.mxtech.videoplayer.ad",
        "com.mxtech.videoplayer.pro",
        "com.amazon.avod.thirdpartyclient",
        "com.disney.disneyplus"
    )

    public val MEDIA_KEYWORDS: List<String> = listOf(
        "youtube", "spotify", "netflix", "soundcloud", "twitch", "media", "music", "video", "player", "streaming", "صوتيات", "فيديو", "موسيقى", "مشغل"
    )

    // ==========================================
    // Navigation Apps
    // ==========================================
    public val NAVIGATION_PACKAGES: Set<String> = setOf(
        "com.google.android.apps.maps",
        "com.google.android.apps.mapslite",
        "com.waze",
        "com.here.app.maps",
        "com.yandex.yandexnavi",
        "cz.seznam.mapy",
        "com.mapswithme.maps.pro",
        "net.osmand",
        "net.osmand.plus",
        "com.sygic.aura"
    )

    public val NAVIGATION_KEYWORDS: List<String> = listOf(
        "maps", "navigation", "gps", "ملاحة", "خرائط", "خريطة"
    )

    // ==========================================
    // Financial & Banking Apps
    // ==========================================
    public data class KnownFinancialAppProfile(
        val category: AppCategory,
        val contextualPermissions: Set<String> = emptySet()
    )

    public val FINANCIAL_KEYWORDS: List<String> = listOf(
        "bank", "wallet", "pay", "finance", "money", "credit", "cash",
        "بنك", "مصرف", "كريمي", "فلوس", "جيب", "ون كاش", "فلوسك", "محفظة", "تداول", "حسابي", "مدى", "راجحي", "أهلي"
    )

    public val KNOWN_FINANCIAL_APP_PROFILES: Map<String, KnownFinancialAppProfile> = mapOf(
        "com.ahd.jaib" to KnownFinancialAppProfile(AppCategory.WALLET),
        "com.one.onecustomer" to KnownFinancialAppProfile(AppCategory.WALLET, setOf("android.permission.READ_PHONE_STATE")),
        "com.ykb.bankylite" to KnownFinancialAppProfile(AppCategory.BANKING),
        "com.bankylite.commercial" to KnownFinancialAppProfile(AppCategory.BANKING, setOf("android.permission.READ_PHONE_STATE")),
        "shamil.bank.yemen.bahrain.wallet.money" to KnownFinancialAppProfile(AppCategory.WALLET, setOf("android.permission.READ_PHONE_STATE", "android.permission.CALL_PHONE")),
        "shamil.bank.yemen.bahrain.wallet.pos" to KnownFinancialAppProfile(AppCategory.WALLET, setOf("android.permission.READ_PHONE_STATE", "android.permission.CALL_PHONE")),
        "cac.mobilemoney.app" to KnownFinancialAppProfile(AppCategory.WALLET),
        "com.mobilemoney.business" to KnownFinancialAppProfile(AppCategory.WALLET),
        "com.s2m.yemenpost.customer.android" to KnownFinancialAppProfile(AppCategory.WALLET, setOf("android.permission.CALL_PHONE")),
        "com.s2m.yamenpost.merchant.android" to KnownFinancialAppProfile(AppCategory.WALLET, setOf("android.permission.CALL_PHONE")),
        "co.ysys.floosak" to KnownFinancialAppProfile(AppCategory.WALLET, setOf("android.permission.RECORD_AUDIO")),
        "com.ama.wecashmobileapp" to KnownFinancialAppProfile(AppCategory.WALLET),
        "com.kuraimibank" to KnownFinancialAppProfile(AppCategory.BANKING, setOf("android.permission.READ_PHONE_STATE")),
        "wallet.mfloos.com.mflooswallet" to KnownFinancialAppProfile(AppCategory.WALLET, setOf("android.permission.READ_PHONE_STATE")),
        "wallet.mfloos.com.mflooswallet.customer" to KnownFinancialAppProfile(AppCategory.WALLET, setOf("android.permission.READ_PHONE_STATE")),
        "com.tamkeen.sms" to KnownFinancialAppProfile(AppCategory.WALLET, setOf("android.permission.RECORD_AUDIO")),
        "com.binance.dev" to KnownFinancialAppProfile(AppCategory.WALLET, setOf("android.permission.READ_PHONE_STATE", "android.permission.RECORD_AUDIO"))
    )

    public fun getFinancialProfile(packageName: String): KnownFinancialAppProfile? =
        KNOWN_FINANCIAL_APP_PROFILES[packageName.lowercase().trim()]

    public fun getFinancialCategory(packageName: String): AppCategory? =
        getFinancialProfile(packageName)?.category

    public fun isKnownFinancialApp(packageName: String): Boolean =
        KNOWN_FINANCIAL_APP_PROFILES.containsKey(packageName.lowercase().trim())

    public fun expectsFinancialPermission(packageName: String, permission: String): Boolean =
        getFinancialProfile(packageName)?.contextualPermissions?.contains(permission) == true

    // ==========================================
    // Games
    // ==========================================
    public val GAME_PACKAGE_PREFIXES: List<String> = listOf(
        "com.activision", "com.ea.", "com.gameloft.", "com.supercell",
        "com.epicgames", "com.riotgames", "com.ubisoft", "com.tencent.ig",
        "com.dts.freefireth", "com.pubg", "com.mojang.minecraftpe",
        "com.roblox.client", "com.kiloo.subwaysurf", "com.king.candycrushsaga"
    )

    public val GAME_KEYWORDS: List<String> = listOf(
        "game", "games", "لعبة", "ألعاب", "play"
    )

    // ==========================================
    // Caller ID & Number Books
    // ==========================================
    public val CALLER_ID_KEYWORDS: List<String> = listOf(
        "caller id", "whocalls", "truecaller", "numberbook", "getcontact",
        "هوية المتصل", "كاشف", "دليل", "يمن فون", "كاشف الأرقام", "من المتصل"
    )

    // ==========================================
    // Modified Apps
    // ==========================================
    public val MODIFIED_APP_PATTERNS: List<Pair<String, List<String>>> = listOf(
        "whatsapp" to listOf("الذهبي", "العمر", "بلس", "plus", "gold", "gb", "yo", "fm", "og"),
        "telegram" to listOf("plus", "بلس"),
        "instagram" to listOf("plus", "instander", "بلس")
    )

    public val MODIFIED_APP_KEYWORDS: List<String> = listOf(
        "واتساب الذهبي", "واتساب عمر", "واتس اب عمر", "واتس الذهبي",
        "gbwhatsapp", "yo_whatsapp", "fmwhatsapp", "whatsapp_plus", "ogwhatsapp", "instander"
    )

    // ==========================================
    // Security & VPN
    // ==========================================
    public val VPN_KEYWORDS: List<String> = listOf(
        "vpn", "tunnel", "wireguard", "openvpn", "proxy", "shadowsocks", "v2ray", "بروكسي"
    )

    public val SECURITY_KEYWORDS: List<String> = listOf(
        "security", "antivirus", "firewall", "soornet", "hazm", "malwarebytes", "حماية", "أمان", "جدار ناري"
    )

    // ==========================================
    // Blacklisted / Highly Suspicious Packages
    // ==========================================
    public val DEFAULT_BLACKLISTED_APPS: Set<String> = setOf(
        "com.zhiliaoapp.musically",
        "com.ss.android.ugc.trill",
        "com.lenovo.anyshare.gps",
        "com.uc.browser.en",
        "com.cleanmaster.master",
        "com.duapps.cleaner",
        "com.cmcm.locker"
    )

    // ==========================================
    // System & OS Packages
    // ==========================================
    public val CRITICAL_SYSTEM_PACKAGES: Set<String> = setOf(
        "android",
        "com.android.phone",
        "com.android.server.telecom",
        "com.android.systemui",
        "com.android.settings",
        "com.android.providers.settings"
    )

    public val SYSTEM_PACKAGE_PREFIXES: List<String> = listOf(
        "android",
        "com.android.",
        "com.google.android.ext.services",
        "com.google.android.packageinstaller",
        "com.sec.android.",
        "com.samsung.android.",
        "com.huawei.android.",
        "com.miui.",
        "com.xiaomi."
    )

    // ==========================================
    // Globally Famous & Well-Known Apps
    // ==========================================
    public val GLOBALLY_FAMOUS_PACKAGES: Set<String> = setOf(
        "com.whatsapp", "com.whatsapp.w4b",
        "com.facebook.katana", "com.facebook.lite", "com.facebook.orca", "com.facebook.mlite",
        "com.instagram.android", "com.instagram.lite", "com.instagram.barcelona",
        "org.telegram.messenger", "org.thoughtcrime.securesms",
        "com.twitter.android", "com.twitter.android.lite",
        "com.google.android.youtube", "com.spotify.music",
        "com.netflix.mediaclient", "com.snapchat.android",
        "com.google.android.apps.maps", "com.waze",
        "com.android.chrome", "org.mozilla.firefox",
        "com.google.android.gm", "com.microsoft.office.outlook"
    )

    public val GLOBALLY_FAMOUS_PACKAGE_PREFIXES: List<String> = listOf(
        "com.google.android.apps.",
        "com.microsoft.office.",
        "com.sec.android.app.",
        "com.samsung.android.app."
    )
}
