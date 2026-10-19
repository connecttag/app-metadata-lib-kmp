package org.connecttag.lib.kmp.appmetadata

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AppCategoryDetectorTest {

    @Test
    fun testCategoryFromOrNull() {
        assertEquals(AppCategory.MESSAGING, AppCategory.fromOrNull("MESSAGING"))
        assertEquals(AppCategory.MESSAGING, AppCategory.fromOrNull("CHAT"))
        assertEquals(AppCategory.MESSAGING, AppCategory.fromOrNull("communication"))
        assertEquals(AppCategory.BROWSER, AppCategory.fromOrNull("WEB"))
        assertEquals(AppCategory.BANKING, AppCategory.fromOrNull("FINANCE"))
        assertEquals(AppCategory.MEDIA, AppCategory.fromOrNull("AUDIO"))
        assertEquals(AppCategory.MEDIA, AppCategory.fromOrNull("video"))
        assertEquals(null, AppCategory.fromOrNull("non_existent_category"))
        assertEquals(null, AppCategory.fromOrNull(null))
    }

    @Test
    fun testDetectMessagingApps() {
        assertEquals(
            AppCategory.MESSAGING,
            AppCategoryDetector.detect("com.whatsapp", "WhatsApp")
        )
        assertEquals(
            AppCategory.MESSAGING,
            AppCategoryDetector.detect("org.telegram.messenger", "Telegram")
        )
        assertEquals(
            AppCategory.MESSAGING,
            AppCategoryDetector.detect("com.facebook.orca", "Messenger")
        )
    }

    @Test
    fun testDetectMediaApps() {
        assertEquals(
            AppCategory.MEDIA,
            AppCategoryDetector.detect("com.spotify.music", "Spotify")
        )
        assertEquals(
            AppCategory.MEDIA,
            AppCategoryDetector.detect("com.google.android.youtube", "YouTube")
        )
        assertEquals(
            AppCategory.MEDIA,
            AppCategoryDetector.detect("com.netflix.mediaclient", "Netflix")
        )
    }

    @Test
    fun telegramXUsesItsExactOfficialPackageIdentity() {
        assertEquals(AppCategory.MESSAGING, AppCategoryDetector.detect("org.thunderdog.challegram", "Telegram X"))
        assertEquals(AppCategory.MESSAGING, AppCategoryDetector.detect("ORG.THUNDERDOG.CHALLEGRAM"))
        assertEquals(AppCategory.USER_APP, AppCategoryDetector.detect("org.thunderdog.challegram.fake"))
    }

    @Test
    fun testDetectBrowsers() {
        assertEquals(
            AppCategory.BROWSER,
            AppCategoryDetector.detect("com.android.chrome", "Chrome")
        )
        assertEquals(
            AppCategory.BROWSER,
            AppCategoryDetector.detect("org.mozilla.firefox", "Firefox")
        )
    }

    @Test
    fun testDetectModifiedApps() {
        assertEquals(
            AppCategory.MODIFIED_APPS,
            AppCategoryDetector.detect("com.whatsapp", "واتساب الذهبي")
        )
        assertEquals(
            AppCategory.MODIFIED_APPS,
            AppCategoryDetector.detect("com.gbwhatsapp", "GBWhatsApp")
        )
    }

    @Test
    fun testDetectBankingAndWallets() {
        assertEquals(
            AppCategory.BANKING,
            AppCategoryDetector.detect("com.alrajhifinancial.app", "الراجحي")
        )
        assertEquals(
            AppCategory.WALLET,
            AppCategoryDetector.detect("com.stc.pay", "STC Pay Wallet")
        )
    }

    @Test
    fun testDetectSystemCriticalApps() {
        assertEquals(
            AppCategory.SYSTEM_CRITICAL,
            AppCategoryDetector.detect("android", "Android System", isSystem = true, uid = 1000)
        )
        assertEquals(
            AppCategory.SYSTEM_CRITICAL,
            AppCategoryDetector.detect("com.android.phone", "Phone", isSystem = true, uid = 1001)
        )
    }

    @Test
    fun testDetectCallerIdApps() {
        assertEquals(
            AppCategory.CALLER_ID,
            AppCategoryDetector.detect("com.truecaller", "Truecaller")
        )
    }

    @Test
    fun testAppTrustCalculation() {
        assertEquals(AppTrust.TRUSTED, AppTrust.fromRiskScore(10, AppCategory.MESSAGING))
        assertEquals(AppTrust.TRUSTED, AppTrust.fromRiskScore(80, AppCategory.SYSTEM))
        assertEquals(AppTrust.NORMAL, AppTrust.fromRiskScore(30, AppCategory.USER_APP))
        assertEquals(AppTrust.CAUTION, AppTrust.fromRiskScore(60, AppCategory.USER_APP))
        assertEquals(AppTrust.SUSPICIOUS, AppTrust.fromRiskScore(85, AppCategory.USER_APP))
        assertEquals(AppTrust.BLOCKED, AppTrust.fromRiskScore(95, AppCategory.USER_APP))
    }

    @Test
    fun testInstallSourceTrust() {
        assertEquals(InstallSourceTrust.PLAY_STORE, InstallSourceTrust.fromInstaller("com.android.vending"))
        assertEquals(InstallSourceTrust.THIRD_PARTY_STORE, InstallSourceTrust.fromInstaller("org.fdroid.fdroid"))
        assertEquals(InstallSourceTrust.UNKNOWN, InstallSourceTrust.fromInstaller("com.unknown.installer"))
        assertEquals(InstallSourceTrust.UNKNOWN, InstallSourceTrust.fromInstaller(null))
    }
}
