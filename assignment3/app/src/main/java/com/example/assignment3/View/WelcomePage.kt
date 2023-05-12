package com.example.assignment3.View

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment3.R


@Composable
fun WelcomePage(navigateToSignUp : () -> Unit, navigateToSignIn : () -> Unit){
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(0.dp)
        .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val welcomeIcon1 = painterResource(id = R.drawable.welcome_1)
        Image(painter = welcomeIcon1, contentDescription = null, modifier = Modifier
            .fillMaxWidth(fraction = 0.7f)
            .padding(top = 130.dp)
        )
        
        Text(text = "Welcome", fontFamily = FontFamily.Cursive, fontSize = 48.sp )
        Text(text = "Read without limits", fontFamily = FontFamily.SansSerif, fontSize = 18.sp, fontWeight = FontWeight.Light)

        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = navigateToSignUp,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(193,103,207), contentColor = Color.White),
                modifier = Modifier
                    .padding(top = 50.dp)
                    .height(50.dp)
                    .width(300.dp),
                shape = RoundedCornerShape(30),
            ) {
                Text(text = stringResource(R.string.create_account), fontSize = 20.sp, fontFamily = FontFamily.SansSerif)
            }

            Button(
                onClick =  navigateToSignIn,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color(193,103,207) ),
                border = BorderStroke(width = 1.dp, color = Color(193,103,207)),
                shape = RoundedCornerShape(30),
                modifier = Modifier
                    .padding(top = 30.dp)
                    .height(50.dp)
                    .width(300.dp)

            ) {
                Text(text = stringResource(R.string.login),  fontSize = 20.sp)
            }
        }
    }
}

//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun WelcomePagePreview(){
//    WelcomePage()
//}