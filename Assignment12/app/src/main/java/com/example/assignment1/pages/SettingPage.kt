package com.example.assignment1.pages

import android.widget.Switch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.assignment1.R
import androidx.compose.material.*

@Composable
fun SettingPage(navigateBack : () -> Unit){
    Column(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
//        .background(color = Color.White)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.08f)
            .background(color = Color.White)) {
            IconButton(onClick = navigateBack) {
                val backArrow = painterResource(id = R.drawable.back)
                Image(painter = backArrow, contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .padding(top = 8.dp, start = 15.dp))
            }
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction = 0.35f)
            .background(color = Color.White)
            .padding(top = 40.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(modifier = Modifier.fillMaxHeight(fraction = 1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(modifier = Modifier
                    .padding(15.dp)
                    .fillMaxSize(fraction = 0.3f)
                    .aspectRatio(1f)
                    .background(color = Color(196, 196, 196), shape = CircleShape)
                ) {}
                Text(text = stringResource(R.string.nickname), fontSize = 16.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 15.dp))

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(9, 52, 73), contentColor = Color.White),
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .height(55.dp)
                        .width(150.dp)
                ) {
                    Text(text = stringResource(R.string.edit_profile), fontSize = 16.sp)
                }

            }
        }
        
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.White)
            .verticalScroll(rememberScrollState()).padding(top = 40.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(10){
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = "Bla, Bla i am some setting", fontSize = 16.sp)

                    val checkedState = remember { mutableStateOf(true) }
                    Switch(
                        checked = checkedState.value,
                        onCheckedChange = { checkedState.value = it }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun SettingsPreview(){
    val navController = rememberNavController()
    SettingPage(navigateBack = {navController.popBackStack("main_page", inclusive = false)})
}