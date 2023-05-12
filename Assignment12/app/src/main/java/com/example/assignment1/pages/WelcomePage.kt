package com.example.assignment1.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment1.R

@Composable
fun WelcomePage(navigateToSignUp : () -> Unit, navigateToSignIn : () -> Unit){
    val backGround = painterResource(id = R.drawable.wallpaperflare_1)
    Image(painter = backGround, contentDescription = null, modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(), contentScale = ContentScale.Crop)
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //fontFamily = FontFamily(Font(R.font.bellota_text_bold_italic))
        Text(text = stringResource(R.string.welcome), color = Color.White,  fontSize = 54.sp, fontWeight = FontWeight.ExtraBold , modifier = Modifier.padding(top = 190.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.7f)
                .padding(top = 150.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = navigateToSignUp,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color(5,34,69)),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .height(50.dp)
                    .width(180.dp)
            ) {
                // fontFamily = FontFamily(Font(R.font.open_sans_semibold)),
                Text(text = stringResource(R.string.sign_up), fontSize = 20.sp)
            }


            Button(
                onClick =  navigateToSignIn,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent, contentColor = Color.White),
                border = BorderStroke(width = 2.dp, color = Color.White),
                modifier = Modifier
                    .padding(top = 40.dp)
                    .height(50.dp)
                    .width(180.dp)
            ) {
                //fontFamily = FontFamily(Font(R.font.open_sans_semibold)),
                Text(text = stringResource(R.string.sign_in),  fontSize = 20.sp)
            }
        }

    }
}



//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun WelcomePagePreview() {
//    Assignment1Theme {
//        val navController = rememberNavController()
//        WelcomePage(navigateToSignIn = {navController.navigate("sign_in_page")})
//    }
//}