package org.connecttag.lib.metadata

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import java.security.MessageDigest

/**
 * Android implementation of [AppMetadataProvider] that extracts metadata and certificates from [PackageManager].
 */
public open class AndroidAppMetadataProvider(
    private val context: Context
) : AppMetadataProvider {

    override fun getAppInfo(): ApplicationInfo {
        return getAppInfo(context.packageName)
    }

    public open fun getAppInfo(targetPackageName: String): ApplicationInfo {
        val pm = context.packageManager
        val appName = try {
            val appInfo = pm.getApplicationInfo(targetPackageName, 0)
            pm.getApplicationLabel(appInfo).toString()
        } catch (_: Exception) {
            targetPackageName
        }

        val isSystem = try {
            val appInfo = pm.getApplicationInfo(targetPackageName, 0)
            (appInfo.flags and android.content.pm.ApplicationInfo.FLAG_SYSTEM) != 0
        } catch (_: Exception) {
            false
        }

        val uid = try {
            pm.getApplicationInfo(targetPackageName, 0).uid
        } catch (_: Exception) {
            10000
        }

        val category = AppCategoryDetector.detect(
            packageName = targetPackageName,
            appLabel = appName,
            isSystem = isSystem,
            uid = uid
        )

        val isPlay = isInstalledFromGooglePlay(targetPackageName)
        val signature = getAppSignature(targetPackageName)
        val installTime = getFirstInstallTime(targetPackageName)
        val (vName, vCode) = getVersionInfo(targetPackageName)

        return ApplicationInfo(
            applicationName = appName,
            packageName = targetPackageName,
            versionName = vName,
            versionCode = vCode,
            firstInstallAt = installTime,
            isInstalledFromGooglePlay = isPlay,
            appSignature = signature,
            category = category,
            trust = if (isSystem) AppTrust.TRUSTED else if (category == AppCategory.MODIFIED_APPS) AppTrust.SUSPICIOUS else AppTrust.NORMAL
        )
    }

    override fun isInstalledFromGooglePlay(): Boolean {
        return isInstalledFromGooglePlay(context.packageName)
    }

    public open fun isInstalledFromGooglePlay(targetPackageName: String): Boolean {
        val installer = try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                context.packageManager.getInstallSourceInfo(targetPackageName).installingPackageName
            } else {
                @Suppress("DEPRECATION")
                context.packageManager.getInstallerPackageName(targetPackageName)
            }
        } catch (_: Exception) {
            null
        }
        return installer != null && installer == "com.android.vending"
    }

    override fun getAppSignature(): String {
        return getAppSignature(context.packageName)
    }

    public open fun getAppSignature(targetPackageName: String): String {
        return try {
            val pm = context.packageManager
            val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                pm.getPackageInfo(targetPackageName, PackageManager.GET_SIGNING_CERTIFICATES)
            } else {
                @Suppress("DEPRECATION")
                pm.getPackageInfo(targetPackageName, PackageManager.GET_SIGNATURES)
            }

            val signatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.signingInfo?.apkContentsSigners
            } else {
                @Suppress("DEPRECATION")
                packageInfo.signatures
            }

            signatures?.firstOrNull()?.toByteArray()?.sha256().orEmpty()
        } catch (_: Exception) {
            ""
        }
    }

    override fun getFirstInstallTime(): Long {
        return getFirstInstallTime(context.packageName)
    }

    public open fun getFirstInstallTime(targetPackageName: String): Long {
        return try {
            context.packageManager.getPackageInfo(targetPackageName, 0).firstInstallTime
        } catch (_: Exception) {
            0L
        }
    }

    private fun getVersionInfo(targetPackageName: String): Pair<String, Long> {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(targetPackageName, 0)
            val vName = packageInfo.versionName ?: ""
            val vCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                @Suppress("DEPRECATION")
                packageInfo.versionCode.toLong()
            }
            Pair(vName, vCode)
        } catch (_: Exception) {
            Pair("", 0L)
        }
    }

    private fun ByteArray.sha256(): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(this)
        return hash.joinToString("") {
            String.format("%02x", it)
        }
    }
}
