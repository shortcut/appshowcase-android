package io.shortcut.showcase.util.extensions

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast


fun Context.isAppInstalled(packageName: String): Boolean =
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getPackageInfo(
                packageName,
                PackageManager.PackageInfoFlags.of(PackageManager.GET_ACTIVITIES.toLong())
            )
        } else {
            @Suppress("DEPRECATION")
            packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_ACTIVITIES
            )
        }
        true
    } catch (e: Exception) {
        false
    }

fun Context.launchApp(packageId: String) {
    val pm: PackageManager = packageManager
    val launchIntent = pm.getLaunchIntentForPackage(packageId)
    if (launchIntent != null) {
        startActivity(launchIntent)
    } else {
        Toast.makeText(this, "Package not found", Toast.LENGTH_SHORT).show()
    }
}