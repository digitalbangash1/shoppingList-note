package com.example.shoppinglist


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shoppinglist.screen.AboutScreen
import com.example.shoppinglist.screen.HomeScreen
import com.example.shoppinglist.screen.MyListScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.MyList.route) {
            MyListScreen()
        }

        composable(route = BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarScreen.About.route) {
            AboutScreen()
        }
    }
}