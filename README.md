# ConnectTag App Metadata Library (`app-metadata-lib-kmp`)

[![JitPack](https://jitpack.io/v/connecttag/app-metadata-lib-kmp.svg)](https://jitpack.io/#connecttag/app-metadata-lib-kmp)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

مكتبة متعددة المنصات (Kotlin Multiplatform) ذكية وخفيفة الوزن لاستخراج بيانات التطبيقات، وتصنيف فئاتها تلقائياً، والتحقق من مصادر التثبيت ومستويات الثقة الأمنية:
- **Android** (API 24+)
- **Desktop JVM** (Windows, macOS, Linux)

---

## 📦 التثبيت والإضافة للمشروع (Installation via JitPack)

### 1. إضافة مستودع JitPack في `settings.gradle.kts`:
```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### 2. إضافة التبعية في `build.gradle.kts`:
```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("com.github.connecttag:app-metadata-lib-kmp:1.0.0")
        }
    }
}
```

---

## 🌟 الميزات الأساسية (Key Features)

### 1. محرك التصنيف الذكي الموحد (`AppCategoryDetector`):
محرك ذكي خالص بـ Kotlin Multiplatform (SSOT) يصنف التطبيقات تلقائياً اعتماداً على الحزمة، العنوان، والـ UID:
- **التطبيقات المعدلة والمهكرة (`MODIFIED_APPS`):** اكتشاف فوري لنسخ واتساب بلس، GBWhatsApp، YoWhatsApp، Instander، إلخ.
- **تطبيقات البنوك والمحافظ (`BANKING` & `WALLET`):** تصنيف دقيق للتطبيقات المالية.
- **كواشف الأرقام (`CALLER_ID`):** اكتشاف برامج مثل Truecaller و NumberBook وتطبيقات كشف المتصل.
- **المتصفحات، شبكات VPN، المشغلات، وتطبيقات النظام الحساسة.**

```kotlin
import org.connecttag.lib.metadata.AppCategoryDetector

val category = AppCategoryDetector.detect(
    packageName = "com.gbwhatsapp",
    appLabel = "GBWhatsApp",
    isSystem = false
)
// Result: AppCategory.MODIFIED_APPS
```

### 2. تقييم الثقة ومصدر التثبيت (`AppTrust` & `InstallSourceTrust`):
- فحص ما إذا كان التطبيق مثبتاً عبر Google Play Store أو متاجر الشركات الرسمية (Samsung, Huawei, Xiaomi) أو عبر تثبيت جانبي غير موثوق (Sideloaded APK).
- حساب مستوى أمان التطبيق (`TRUSTED`، `NORMAL`، `CAUTION`، `SUSPICIOUS`، `BLOCKED`).

```kotlin
import org.connecttag.lib.metadata.InstallSourceTrust

val sourceTrust = InstallSourceTrust.fromInstaller(installerPackageName)
// Result: InstallSourceTrust.PLAY_STORE, THIRD_PARTY_STORE, or UNKNOWN
```

### 3. مستخرج بيانات أندرويد المتكامل (`AndroidAppMetadataProvider`):
في بيئة أندرويد، استخرج فورياً كامل بيانات التطبيق أو أي تطبيق مثبت، بما في ذلك بصمة شهادة التوقيع (SHA-256 Certificate Fingerprint) وتاريخ التثبيت وربطها فورياً بمحرك التصنيف:

```kotlin
import org.connecttag.lib.metadata.AndroidAppMetadataProvider

val provider = AndroidAppMetadataProvider(context)
val info = provider.getAppInfo("com.example.targetapp")

println("Name: ${info.applicationName}")
println("Category: ${info.category}")
println("Installed from Play Store: ${info.isInstalledFromGooglePlay}")
println("Certificate Fingerprint: ${info.appSignature}")
```

---

## 🛡️ الترخيص (License)
مرخصة تحت رخصة [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt).
