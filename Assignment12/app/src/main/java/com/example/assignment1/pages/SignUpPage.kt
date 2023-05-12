package com.example.assignment1.pages

//import android.content.Context
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
//import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
//import androidx.navigation.NavHostController
import com.example.assignment1.R
//import com.example.assignment1.Users
import com.example.assignment1.classes.User
import com.example.assignment1.classes.UserViewModel
//import androidx.activity.viewModels

//, userViewModel: UserViewModel

@Composable
fun SignUpPage(navigateBack : () -> Unit , userViewModel: UserViewModel, navController: NavHostController) {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val backGround = painterResource(id = R.drawable.wallpaperflare_2)
    Image(
        painter = backGround, contentDescription = null, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(), contentScale = ContentScale.Crop
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //            fontFamily = FontFamily(Font(R.font.bellota_text_bold_italic)),
        Text(
            text = stringResource(R.string.join_us),
            color = Color.White, fontSize = 54.sp,

            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(top = 140.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.7f)
                .padding(top = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val emailValue = remember { mutableStateOf("") }
            val passwordValue = remember { mutableStateOf("") }
//            val passwordConfirmValue = remember { mutableStateOf("") }
            val passwordVisibility = remember { mutableStateOf(false) }



            OutlinedTextField(
                value = emailValue.value,
                onValueChange = { emailValue.value = it },
                label = {
                    Text(
                        text = stringResource(id = R.string.label_email),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 20.dp)
            )

            OutlinedTextField(
                value = passwordValue.value,
                onValueChange = { passwordValue.value = it },
                label = {
                    Text(
                        stringResource(id = R.string.label_password),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.White),
//                visualTransformation = if (passwordVisibility.value) VisualTransformation.None
//                else PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 20.dp)

            )

            Button(
                onClick = {
                    if(emailValue.value.isNotEmpty() && passwordValue.value.isNotEmpty()){
                        val user = User(
                            email = emailValue.value,
                            password = passwordValue.value
                        )
                        userViewModel.addUser(user)
                        onBackPressedDispatcher?.onBackPressed()
                    }else{
                        println("invalid submission")
                    }

                },


                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color(5, 34, 69)
                ),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .height(50.dp)
                    .width(220.dp)
            ) {
                //fontFamily = FontFamily(Font(R.font.open_sans_semibold))
                Text(text = stringResource(R.string.big_create_acc), fontSize = 20.sp)
            }


            TextButton(onClick = navigateBack, modifier = Modifier.padding(top = 100.dp)) {
                Text(
                    text = stringResource(id = R.string.back),
                    color = Color.White,
                    fontSize = 16.sp,
                    style = TextStyle(textDecoration = TextDecoration.Underline)
                )
            }

        }

    }
}

//@Composable
//fun addUserInDB(
//    email:String,
//    password : String,
//    userViewModel: UserViewModel,
//    navController: NavHostController
//) {
//
//
//
//}




//
//@Preview(showSystemUi = true)
//@Composable
//fun SignUpPagePreview() {
////        val navController = rememberNavController()
//        //navigateBack = navController
//    SignUpPage()
//}
