package org.connecttag.lib.kmp.appmetadata

import kotlinx.serialization.Serializable

/**
 * Lightweight, pure-Kotlin descriptor of an app's ecosystem identity
 * (signing certificate and shared UID).
 */
@Serializable
public data class ClusterableApp(
    val packageName: String,
    val appName: String = "",
    val sharedUserId: String? = null,
    val certificateFingerprint: String? = null,
    val certificateSubject: String? = null,
    val isSystem: Boolean = false
)

/**
 * A group of apps sharing the same signing certificate.
 */
public data class SignatureCluster<T>(
    val certFingerprint: String,
    val certSubject: String,
    val apps: List<T>
)

/**
 * A group of apps sharing the same Linux UID / sharedUserId.
 */
public data class SharedUidCluster<T>(
    val sharedUserId: String,
    val apps: List<T>
)

/**
 * Aggregated ecosystem report detailing cross-app signature groups and shared UIDs.
 */
public data class EcosystemReport<T>(
    val signatureClusters: List<SignatureCluster<T>>,
    val sharedUidClusters: List<SharedUidCluster<T>>,
    val totalSharedUidApps: Int,
    val totalCertGroups: Int
)

/**
 * Single Source of Truth for clustering installed applications by certificate signatures
 * and shared user IDs (Ecosystem Analysis).
 * Pure Kotlin Multiplatform (Zero platform dependencies).
 */
public object AppEcosystemClusterer {

    /**
     * Clusters apps using a custom extractor function.
     */
    public fun <T> cluster(
        items: Collection<T>,
        minSignatureClusterSize: Int = 2,
        extract: (T) -> ClusterableApp
    ): EcosystemReport<T> {
        val certMap = mutableMapOf<String, Pair<String, MutableList<T>>>()
        val sharedUidMap = mutableMapOf<String, MutableList<T>>()

        for (item in items) {
            val app = extract(item)

            // 1. Shared UID
            val sharedId = app.sharedUserId?.trim()
            if (!sharedId.isNullOrEmpty()) {
                sharedUidMap.getOrPut(sharedId) { mutableListOf() }.add(item)
            }

            // 2. Signing Certificate Fingerprint
            val fp = app.certificateFingerprint?.trim()
            if (!fp.isNullOrEmpty()) {
                val subject = app.certificateSubject?.trim().takeUnless { it.isNullOrBlank() } ?: "Unknown Certificate"
                val entry = certMap.getOrPut(fp) { Pair(subject, mutableListOf()) }
                entry.second.add(item)
            }
        }

        val sigClusters = certMap.map { (fp, pair) ->
            SignatureCluster(
                certFingerprint = fp,
                certSubject = pair.first,
                apps = pair.second
            )
        }
            .filter { it.apps.size >= minSignatureClusterSize }
            .sortedByDescending { it.apps.size }

        val uidClusters = sharedUidMap.map { (uid, apps) ->
            SharedUidCluster(
                sharedUserId = uid,
                apps = apps
            )
        }.sortedByDescending { it.apps.size }

        val totalSharedUidApps = uidClusters.sumOf { it.apps.size }

        return EcosystemReport(
            signatureClusters = sigClusters,
            sharedUidClusters = uidClusters,
            totalSharedUidApps = totalSharedUidApps,
            totalCertGroups = sigClusters.size
        )
    }

    /**
     * Direct clustering for collections of [ClusterableApp].
     */
    public fun cluster(
        apps: Collection<ClusterableApp>,
        minSignatureClusterSize: Int = 2
    ): EcosystemReport<ClusterableApp> = cluster(apps, minSignatureClusterSize) { it }
}
