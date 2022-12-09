package io.shortcut.showcase.presentation.idle.data

import io.shortcut.showcase.data.mapper.GeneralCategory

data class CarouselApp(
    val iconURL: String,
    val title: String,
    val generalCategory: GeneralCategory,
    val shortDescription: String
)

val bankID: CarouselApp = CarouselApp(
    iconURL = "https://is1-ssl.mzstatic.com/image/thumb/Purple122/v4/75/6e/7f/756e7fcf-6703-7a62-0824-8af6ad659e24/AppIcon-0-0-1x_U007emarketing-0-0-0-10-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/512x512bb.jpg",
    title = "BankID",
    generalCategory = GeneralCategory.BUSINESS,
    shortDescription = "BankID lets you identify yourself, confirm payments and sign documents."
)

val ruter: CarouselApp = CarouselApp(
    iconURL = "https://is4-ssl.mzstatic.com/image/thumb/Purple122/v4/20/0a/a6/200aa643-540b-81c8-ea6f-4f1ebb328ac2/AppIcon-Prod-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/512x512bb.jpg",
    title = "Ruter",
    generalCategory = GeneralCategory.TRAVEL,
    shortDescription = "Everything you need to ride public transport, all in one app."
)

val bulderBank: CarouselApp = CarouselApp(
    iconURL = "https://is2-ssl.mzstatic.com/image/thumb/Purple112/v4/e5/99/72/e5997233-5ff3-210c-ffa6-696473118048/AppIcon-1x_U007emarketing-0-7-0-0-85-220.png/512x512bb.jpg",
    title = "Bulder Bank",
    generalCategory = GeneralCategory.BUSINESS,
    shortDescription = "Bulder Bank er en ny bankopplevelse. Designet for ditt mobile liv."
)

val rema1000: CarouselApp = CarouselApp(
    iconURL = "https://is2-ssl.mzstatic.com/image/thumb/Purple122/v4/d6/cc/08/d6cc0828-0c74-a793-23b2-3af916522c08/AppIcon-1x_U007emarketing-0-7-0-sRGB-85-220.png/512x512bb.jpg",
    title = "Æ",
    generalCategory = GeneralCategory.SHOPPING,
    shortDescription = "Med Æ kan du spare penger på hver handletur du gjør hos REMA 1000."
)

val cutters: CarouselApp = CarouselApp(
    iconURL = "https://is1-ssl.mzstatic.com/image/thumb/Purple112/v4/cb/aa/f5/cbaaf59b-eac7-fbfe-6adc-b2cb3fbad923/AppIcon-0-0-1x_U007emarketing-0-0-0-5-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/512x512bb.jpg",
    title = "Cutters",
    generalCategory = GeneralCategory.BUSINESS,
    shortDescription = "Cutters offers smarter haircuts for a fixed, low price."
)

val vipps: CarouselApp = CarouselApp(
    iconURL = "https://is5-ssl.mzstatic.com/image/thumb/Purple122/v4/6f/4f/ec/6f4fecee-808e-0929-5b96-238d409e23e6/AppIcon-0-1x_U007emarketing-0-7-0-85-220.png/512x512bb.jpg",
    title = "Vipps",
    generalCategory = GeneralCategory.BUSINESS,
    shortDescription = "It’s as safe as a regular bank transfer, just way easier!"
)

val carouselAppList = listOf(bankID, ruter, bulderBank, rema1000, cutters, vipps)