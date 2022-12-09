package io.shortcut.showcase.presentation.home.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.shortcut.showcase.presentation.home.view.HomeScreen
import io.shortcut.showcase.presentation.idle.view.IdleScreen
import io.shortcut.showcase.ui.theme.ShowcaseThemeCustom

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavigation(
    navController: NavHostController,
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
                onIdleClick = { navController.navigateSingleTopTo(Idle.route)}
            )
        }
        composable(route = Idle.route) {
            IdleScreen(
                onBackClick = { navController.navigateSingleTopTo(Home.route)}
            )
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }