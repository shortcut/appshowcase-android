package io.shortcut.showcase.presentation.home.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.shortcut.showcase.presentation.home.view.HomeScreen
import io.shortcut.showcase.presentation.home.view.HomeViewModel
import io.shortcut.showcase.presentation.home.view.ScreenshotScreen
import io.shortcut.showcase.presentation.idle.view.IdleScreen
import io.shortcut.showcase.ui.theme.ShowcaseThemeCustom

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavigation(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val systemUiController: SystemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = ShowcaseThemeCustom.colors.ShowcaseBackground)
    systemUiController.isNavigationBarVisible = false

    NavHost(
        navController = navController,
        startDestination = Home.route
    ) {
        composable(route = Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onIdleClick = { navController.navigateSingleTopTo(Idle.route) },
                onScreenshotClick = { navController.navigateToScreenshotGallery(it) }
            )
        }
        composable(route = Idle.route) {
            IdleScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(route = ScreenshotGallery.route) { backstackEntry ->
            ScreenshotScreen(
                viewModel = viewModel,
                onBackArrowClick = { navController.popBackStack() },
                startIndex = backstackEntry.arguments?.getString("startIndex")?.toIntOrNull() ?: 0
            )
        }
    }
}

fun NavHostController.navigateToScreenshotGallery(startIndex: Int) =
    navigate(route = "screenshot_gallery/$startIndex")

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }