package io.shortcut.showcase.presentation.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.data.mapper.GeneralCategory
import io.shortcut.showcase.presentation.home.view.HomeScreen
import io.shortcut.showcase.presentation.home.view.ScreenshotScreen
import io.shortcut.showcase.presentation.idle.view.IdleScreen
import io.shortcut.showcase.presentation.showAll.ShowAllScreen
import io.shortcut.showcase.ui.theme.ShowcaseThemeCustom
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

@Composable
fun MainNavigation(
    navController: NavHostController
) {
    val systemUiController: SystemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = ShowcaseThemeCustom.colors.ShowcaseBackground)
    systemUiController.isNavigationBarVisible = false

    NavHost(
        navController = navController,
        startDestination = ShowcaseDestination.Home.route
    ) {
        composable(route = ShowcaseDestination.Home.route) {
            HomeScreen(
                onNavDestinations = { destinations ->
                    homeNavigation(destinations, navController)
                }
            )
        }
        composable(route = ShowcaseDestination.ShowAllApps.route) { _ ->
            ShowAllScreen(
                onBackClick = { navController.popBackStack() },
                onNavDestinations = { destinations ->
                    homeNavigation(destinations, navController)
                }
            )
        }
        composable(route = ShowcaseDestination.Idle.route) {
            IdleScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(route = ShowcaseDestination.ScreenshotGallery.route) { backstackEntry ->
            ScreenshotScreen(
                onBackArrowClick = { navController.popBackStack() },
                startIndex = backstackEntry.arguments?.getString("startIndex")?.toIntOrNull() ?: 0,
                images = backstackEntry.arguments?.getString("imageList")?.let {
                    val stringListSerializer = ListSerializer(String.serializer())
                    Json.decodeFromString(stringListSerializer, it)
                } ?: emptyList()
            )
        }
    }
}

private fun homeNavigation(
    destinations: HomeScreenDestinations,
    navController: NavHostController
) {
    when (destinations) {
        HomeScreenDestinations.IdleScreen -> navController.navigateSingleTopTo(
            ShowcaseDestination.Idle.route
        )

        is HomeScreenDestinations.ScreenshotGallery -> navController.navigateToScreenshotGallery(
            destinations.imageIndex,
            destinations.imageUrls
        )

        is HomeScreenDestinations.ShowAllAppsScreen -> navController.navigateToShowAllAppsScreen(
            destinations.country,
            destinations.category
        )
    }
}

fun NavHostController.navigateToScreenshotGallery(startIndex: Int, images: List<String>) {
    val stringListSerializer = ListSerializer(String.serializer())
    val data = Json.encodeToString(stringListSerializer, images)
    navigate(route = "screenshot_gallery/?startIndex=$startIndex&imageList=$data")
}

fun NavHostController.navigateToShowAllAppsScreen(country: Country, category: GeneralCategory) {
    navigate(route = "show_all_apps/?country=${country.name}&category=${category.name}")
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }