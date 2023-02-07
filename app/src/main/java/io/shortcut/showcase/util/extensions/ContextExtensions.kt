package io.shortcut.showcase.util.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build

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
        launchPlayStorePage(packageName = packageId)
    }
}

fun Context.launchPlayStorePage(packageName: String) {
    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
    } catch (e: ActivityNotFoundException) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
        )
    }
}