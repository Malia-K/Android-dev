package com.example.assignment3.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment3.R

@Composable
fun MainPage(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(color = Color.White)
        .padding(start = 20.dp)

    ) {
        var searchIcon = painterResource(id = R.drawable.search_icon)

        val searchValue = remember { mutableStateOf("") }
        Text(text = "What do you want to read today?", modifier = Modifier.padding(top = 20.dp), fontFamily = FontFamily.SansSerif, fontSize = 16.sp, color = Color(79,79,79))

        OutlinedTextField(
            leadingIcon = {
                Icon(painter = searchIcon, contentDescription = null, modifier = Modifier.width(18.dp).height(20.dp) )
            },
            value = searchValue.value,
            onValueChange = { searchValue.value = it },
            placeholder = { Text(text = "Search", color = Color(142,142,147),) },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color(142,142,147)),
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(top = 15.dp),

        )
    }
}


@Preview
@Composable
fun MainPagePrev(){
    MainPage()
}