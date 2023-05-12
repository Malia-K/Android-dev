package com.example.assignment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignment1.classes.User
import com.example.assignment1.classes.UserRoomDatabase
import com.example.assignment1.classes.UserViewModel
import com.example.assignment1.pages.*
import com.example.assignment1.ui.theme.Assignment1Theme

//val Users: List<User> = ArrayList()


class MainActivity : ComponentActivity() {

    private lateinit var userDb : UserRoomDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userDb = UserRoomDatabase.getDatabase(this)
        setContent {
            val userViewModel: UserViewModel by viewModels()
            Assignment1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "welcome_page") {
                        composable("welcome_page"){
                            WelcomePage(
                                navigateToSignUp = {navController.navigate("sign_up_page")},
                                navigateToSignIn = {navController.navigate("sign_in_page")}
                            )
                        }

                        composable("sign_in_page"){
                            SignInPage(
                                navigateBack = {navController.popBackStack("welcome_page", inclusive = false)},
                                navigateToSignUp = {navController.navigate("sign_up_page")},
                                navigateToMainPage = {navController.navigate("main_page")},
                                userViewModel,
                                navController,
                                userDb
                            )
                        }

                        composable("sign_up_page"){
                            SignUpPage (
                                navigateBack = {navController.popBackStack("welcome_page", inclusive = false)},
                                userViewModel,
                                navController
                            )
                        }

                        composable("main_page"){
                            MainPage (
                                navigateToSettingPage = {navController.navigate("setting_page")}
                            )
                        }

                        composable("setting_page"){
                            SettingPage (
                                navigateBack = {navController.popBackStack("main_page", inclusive = false)}
                            )
                        }

                    }
                }
            }
        }
    }
}


