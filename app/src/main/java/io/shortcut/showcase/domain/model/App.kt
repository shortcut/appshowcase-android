package io.shortcut.showcase.domain.model

data class AppObject(
    val title: String?, // The shortest title of the app. Eg "Vipps".
    val iconUrl: String?, // An image url of the app icon. Will be used with Coil.
    val publisher: String, // Who published the app.
    val country: String, // This could actually be more fields, keep it to one.
    val screenshots: List<String>?, // String array of the image urls fetched from either App Store or Play Store.
    val totalInstalls: String?, // This is the total install on both platforms. Preferably formatted. Eg: 10 232 321 installs -> 10K +.
    val shortDescription: String, // A short app description.

    val generalCategory: String, // The overall category of an app, eg "Business" -> VERY IMPORTANT FOR FILTERING APPS IN THE CLIENT. This needs to be based on the sub category.
    //val subCategory: String, // The sub category of the app which will only be used for showing in details.

    val highestRating: Float, //  Based on the highest rating of either iOS/Android.
    val totalHistogram: Map<String, Int>, // 5 to 1 star as String, the amount of each rating as Int. This can also be the total histogram of ratings in both platform.

    val androidPackageID: String?, // This is the Android package ID and will be used to either open an installed app or the Play Store.
)
