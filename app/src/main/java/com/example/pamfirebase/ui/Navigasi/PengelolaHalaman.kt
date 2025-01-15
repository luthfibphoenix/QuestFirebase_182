package com.example.pamfirebase.ui.Navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pamfirebase.ui.view.DetailView
import com.example.pamfirebase.ui.view.HomeView
import com.example.pamfirebase.ui.view.InsertMhsView


@Composable
fun PengelolaHalaman(
    modifier: Modifier,
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ){
        composable(DestinasiHome.route){
            HomeView(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsert.route)
                },
            )
        }
        composable(DestinasiInsert.route){
            InsertMhsView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.navigate(DestinasiHome.route)
                }
            )
        }
        composable(
            route = DestinasiDetail.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetail.NIM) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val nim = backStackEntry.arguments?.getString(DestinasiDetail.NIM)
            nim?.let {
                DetailView(
                    navigateBack = { navController.popBackStack() },
                    navigateToEdit = { }
                )
            }
        }
    }
}