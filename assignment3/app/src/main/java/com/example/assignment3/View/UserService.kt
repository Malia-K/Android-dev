package com.example.assignment3.View

import com.google.firebase.auth.FirebaseAuth

//
//class UserService {
//
//
//}

fun logout(navigateToWelcomePage: () -> Unit){
    FirebaseAuth.getInstance().signOut()
    navigateToWelcomePage()
    cnt = 0
}


