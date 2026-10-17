package org.connecttag.lib.kmp.appmetadata

import kotlinx.serialization.Serializable

/**
 * Standard data model representing metadata of an application in KMP.
 */
@Serializable
public data class ApplicationInfo(
    val apiKey: String = "",
    val appId: Int = 0,
    val applicationName: String = "",
    val packageName: String = "",
    val versionName: String = "",
    val versionCode: Long = 0L,
    val internalVersionCode: Int = 0,
    val flavor: String = "",
    val buildType: String = "",
    val isDebug: Boolean = false,
    val firstInstallAt: Long = 0L,
    val isInstalledFromGooglePlay: Boolean = false,
    val appSignature: String = "",
    val category: AppCategory = AppCategory.UNKNOWN,
    val trust: AppTrust = AppTrust.NORMAL
)

/**
 * Common contract for querying application metadata across platforms.
 */
public interface AppMetadataProvider {
    public fun getAppInfo(): ApplicationInfo
    public fun isInstalledFromGooglePlay(): Boolean
    public fun getAppSignature(): String
    public fun getFirstInstallTime(): Long
}
