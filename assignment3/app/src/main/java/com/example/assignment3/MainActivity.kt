package com.example.assignment3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignment3.View.*
import com.example.assignment3.ui.theme.Assignment3Theme

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "welcome_page" ){
                        composable("welcome_page"){
                            WelcomePage(
                                navigateToSignUp = {navController.navigate("sign_up_page")},
                                navigateToSignIn = {navController.navigate("sign_in_page")}
                            )
                        }
                        composable("sign_in_page"){
                            SignInPage(
                                navigateToSignUp = {navController.navigate("sign_up_page")},
                                navigateToUserPage = {navController.navigate("user_page")},
                                navigateToAdminPage = {navController.navigate("admin_page")},
                            )
                        }

                        composable("sign_up_page"){
                            SignUpPage(
                                navigateToSignIn = { navController.navigate("sign_in_page")},
                                navigateToMainPage = {navController.navigate("main_page")},
                            )
                        }
                        composable("user_page"){
                            DashboardUserPage(
                                navigateToWelcomePage = {navController.navigate("welcome_page")},
                                navigateBack = {navController.popBackStack()},
                                navigateToBookDetailPage = {navController.navigate("book_detail_page")},
                                refresh = {navController.navigate("user_page")},
                                navigateToFavBookPage = {navController.navigate("fav_book_page")}
                            )
                        }
                        composable("admin_page"){
                            DashboardAdminPage(
                                navigateToWelcomePage = {navController.navigate("welcome_page")},
                                navigateToAddBookPage = {navController.navigate("add_book_page")},
                                navigateToUpdateBookPage = {navController.navigate("update_book_page")},
                                navigateBack = {navController.popBackStack()},
                                navigateToBookDetailPage = {navController.navigate("book_detail_page")},
                                refresh = {navController.navigate("admin_page")},
                                navigateToFavBookPage = {navController.navigate("fav_book_page")},
//                                gameViewModel = viewModel()
                            )
                        }
                        composable("add_book_page"){
                            AddBookPage(
                                navigateBack = {navController.popBackStack()}
                            )
                        }
                        composable("update_book_page"){
                            UpdateBookPage(
                                navigateBack = {navController.popBackStack()}
                            )
                        }
                        composable("book_detail_page"){
                            BookDetailsPage(
                                navigateBack = {navController.popBackStack()},
                                navigateToUserPage = {navController.navigate("user_page")},
                                navigateToAdminPage = {navController.navigate("admin_page")},
                                refresh = {navController.navigate("book_detail_page")}
                            )
                        }
                        composable("fav_book_page"){
                            FavouriteBooksPage(
                                navigateBack = {navController.popBackStack()},
                                navigateToUpdateBookPage = {navController.navigate("update_book_page")},
                                navigateToBookDetailPage = {navController.navigate("book_detail_page")},
                                refresh = {navController.navigate("fav_book_page")}
                            )
                        }

                    }
                }
            }
        }
    }
}
