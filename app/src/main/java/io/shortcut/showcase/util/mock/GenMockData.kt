package io.shortcut.showcase.util.mock

import io.shortcut.showcase.data.local.Screenshots
import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.data.mapper.GeneralCategory
import io.shortcut.showcase.presentation.common.filter.data.CountryFilter
import io.shortcut.showcase.presentation.data.ShowcaseAppUI
import io.shortcut.showcase.presentation.data.ShowcaseBannerUI

fun genMockShowcaseAppUI(): ShowcaseAppUI {
    return ShowcaseAppUI(
        id = "",
        title = "Vipps",
        iconUrl = "https://is3-ssl.mzstatic.com/image/thumb/Purple122/v4/4c/02/32/4c02323b-c2fb-f501-095b-85b94645b14f/AppIcon-0-1x_U007emarketing-0-7-0-85-220.png/512x512bb.jpg",
        publisher = "Vipps",
        country = Country.Norway,
        screenshots = Screenshots(
            imageURLs = listOf(
                "https://is3-ssl.mzstatic.com/image/thumb/Purple112/v4/bc/9a/45/bc9a459f-23f8-8b4c-aaac-ec030c95c93f/abad0f87-e0ea-4368-b77d-a8c7cec8d5ca_04.png/392x696bb.png",
                "https://is2-ssl.mzstatic.com/image/thumb/PurpleSource122/v4/67/b1/3d/67b13dc9-5c18-cfe5-22d8-a79be1cd0d77/3b4fd0eb-3f1d-4825-9a2b-7e85e0b84bbc_02.png/392x696bb.png",
                "https://is1-ssl.mzstatic.com/image/thumb/PurpleSource122/v4/1f/1e/95/1f1e95b4-385f-6eeb-fd44-21d5cea28081/7fe1530c-dca4-4e2d-8678-eb095f85eb14_03.png/392x696bb.png",
                "https://is4-ssl.mzstatic.com/image/thumb/Purple112/v4/9d/16/91/9d1691ea-a45c-590c-f268-90cc88ed6746/aa3a923d-d905-4e74-a709-11e5081646f5_10.png/392x696bb.png",
                "https://is1-ssl.mzstatic.com/image/thumb/Purple112/v4/4f/70/ec/4f70ec1e-02a7-2b26-b680-834f2fb540bf/3d7c363e-9a40-43f0-aba9-40a7c930e9be_09.png/392x696bb.png",
                "https://is2-ssl.mzstatic.com/image/thumb/Purple122/v4/f6/37/aa/f637aa69-99a1-946c-1f50-d23494383b82/fbced448-4302-44ae-ad2a-14ebbfe7d80a_05.png/392x696bb.png",
                "https://is1-ssl.mzstatic.com/image/thumb/Purple122/v4/be/2c/6b/be2c6b6a-943a-26ba-0e28-38e32ee8dd79/ffdfd9c3-a1c4-4f2f-b785-de847ee14499_06.png/392x696bb.png",
                "https://is2-ssl.mzstatic.com/image/thumb/Purple122/v4/6e/71/17/6e7117c6-cde3-4c82-1682-12717e190b4f/221052c2-94aa-479f-a077-b19db4177519_07.png/392x696bb.png",
                "https://is1-ssl.mzstatic.com/image/thumb/Purple122/v4/64/22/b6/6422b6e9-9ef0-4446-84b7-3d3c7a71f000/9d7cabe0-4b68-4ea3-bc99-cc9cafc4e4c1_08.png/392x696bb.png"
            )
        ),
        totalInstalls = "1,000,000+",
        shortDescription = "Pay with Vipps!All you need is the name or number of the person you want to send money. We’ll take care of the rest. It’s as safe as a regular bank transfer, just way easier!With Vipps you’re able to:• Send and request money• Send gifts• View ",
        generalCategory = GeneralCategory.BUSINESS,
        highestRating = "4.76",
        /*totalHistogram = Histogram(
            histogramData = mapOf(
                "1" to 11337,
                "2" to 4452,
                "3" to 17324,
                "4" to 82939,
                "5" to 586544,
            )
        ),*/
        androidPackageID = "no.dnb.vipps",
        onClick = {}
    )
}

fun genMockShowcaseAppUIList(count: Int = 10) = List(count) { genMockShowcaseAppUI() }

fun genMockBanners(): List<ShowcaseBannerUI> {
    return buildList {
        add(ShowcaseBannerUI(id = "0", imageUrl = "https://images.unsplash.com/photo-1669707357879-2daffd3ef7f9?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1665&q=80"))
        add(ShowcaseBannerUI(id = "1", imageUrl = "https://images.unsplash.com/photo-1669705746675-18a4dd315e93?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=3272&q=80"))
        add(ShowcaseBannerUI(id = "2", imageUrl = "https://images.unsplash.com/photo-1669573234406-00c9e3f6ab34?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=3270&q=80"))
    }
}

fun genMockFilterButtons(): List<CountryFilter> {
    return buildList {
        add(CountryFilter(type = Country.Norway, selected = false, onClick = {}))
        add(CountryFilter(type = Country.Sweden, selected = false, onClick = {}))
        add(CountryFilter(type = Country.Denmark, selected = false, onClick = {}))
    }
}
