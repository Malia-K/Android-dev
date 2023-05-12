package com.example.assignment1.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.example.assignment1.MainActivity
import com.example.assignment1.R
import com.example.assignment1.classes.User
import com.example.assignment1.classes.UserRoomDatabase
import com.example.assignment1.classes.UserViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//import org.jetbrains.exposed.sql.Database
//import org.jetbrains.exposed.sql.selectAll
//import org.jetbrains.exposed.sql.transactions.transaction


@Composable
fun SignInPage(navigateBack : () -> Unit, navigateToSignUp : () -> Unit, navigateToMainPage : () -> Unit, userViewModel: UserViewModel, navController: NavHostController, userDb : UserRoomDatabase){
    val backGround = painterResource(id = R.drawable.wallpaperflare_2)
    Image(painter = backGround, contentDescription = null, modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(), contentScale = ContentScale.Crop)
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
    //fontFamily = FontFamily(Font(R.font.bellota_text_bold_italic)),
        Text(text = stringResource(R.string.lets_go),

            color = Color.White, fontSize = 54.sp,
            fontWeight = FontWeight.ExtraBold ,
            modifier = Modifier.padding(top = 140.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.7f)
                .padding(top = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val emailValue = remember { mutableStateOf("") }
            val passwordValue = remember { mutableStateOf("") }

            val passwordVisibility = remember { mutableStateOf(false) }


            OutlinedTextField(
                value = emailValue.value,
                onValueChange = { emailValue.value = it },
                label = { Text(text = stringResource(R.string.label_email), color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold) },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.White),
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            OutlinedTextField(
                value = passwordValue.value,
                onValueChange = { passwordValue.value = it },
                label = { Text(stringResource(R.string.label_password), color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold) },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.White),
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                else PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 20.dp)
            )
            Button(
                onClick = if (Authentication(emailValue.value, passwordValue.value, userDb, userViewModel)) navigateToMainPage else navigateBack,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color(5,34,69)),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .height(50.dp)
                    .width(220.dp)
            ) {
                // fontFamily = FontFamily(Font(R.font.open_sans_semibold)),
                Text(text = stringResource(id =R.string.sign_in ), fontSize = 20.sp)
            }

            Text(text = stringResource(R.string.no_acc), color = Color.White, fontSize = 17.sp, modifier = Modifier.padding(top = 15.dp))
            TextButton(onClick = navigateToSignUp) {
                Text(text = stringResource(R.string.create_acc), color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, style = TextStyle(textDecoration = TextDecoration.Underline))
            }


            TextButton(onClick = navigateBack , modifier = Modifier.padding(top = 70.dp)) {
                Text(text = stringResource(R.string.back)
                    , color = Color.White, fontSize = 16.sp, style = TextStyle(textDecoration = TextDecoration.Underline))
            }

        }

    }
}

//@Preview(showSystemUi = true)
//@Composable
//fun prev(){
//    val navController = rememberNavController()
//    SignInPage(navigateBack = {navController.popBackStack("welcome_page", inclusive = false)}, navigateToSignUp =  {navController.navigate("sign_up_page")},)
//}



private  fun Authentication(email: String, password: String, userDb: UserRoomDatabase ,userViewModel: UserViewModel) : Boolean{
        var isExist = false;
//        userViewModel.getAllUsers()
        if(email.isNotEmpty()){
            var findedUser : MutableLiveData<User> = userViewModel.findUserByEmail(email)

            println(findedUser.value?.email + " " + findedUser.value?.password)
            if(findedUser.value?.password.equals(password)){
                isExist = true
            }

        }


        return isExist;
}
