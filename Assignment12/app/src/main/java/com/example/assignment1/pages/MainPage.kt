package com.example.assignment1.pages

//import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment1.R


@Composable
fun MainPage(navigateToSettingPage : () -> Unit){
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(Color.White)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.08f)
                .background(Color(9, 52, 73))
                .padding(start = 15.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {


            var expanded by remember { mutableStateOf(false) }
            var selectedOption by remember { mutableStateOf("") }
            Box {
                IconButton(onClick = { expanded = true }) {
                    val menu = painterResource(id = R.drawable.menu__1_)
                    Image(painter = menu, contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight(0.5f)
                            .padding(top = 8.dp))
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(onClick = { selectedOption="Profile"}) {
                        Text("Профиль")
                    }
                    DropdownMenuItem(onClick = { selectedOption="Paste"}) {
                        Text("Мои закладки")
                    }
                    Divider()
                    DropdownMenuItem(onClick = navigateToSettingPage) {
                        Text("Настройки")
                    }

                }
            }
        }
        MainPageHeader()
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp),
            modifier = Modifier.padding(top = 2.dp),
            content = {
                items(70){
                    GridBox()
                }

            }
        )


    }
}

@Composable
fun MainPageHeader(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(fraction = 0.05f).padding(start = 15.dp), verticalAlignment = Alignment.CenterVertically ) {
        Text(text = "News", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
    }
}


@Composable
fun GridBox(){
    var red = (0..255).random()
    var blue = (0..255).random()
    var green = (0..255).random()
    Box(
        modifier = Modifier
            .padding(2.dp)
            .aspectRatio(1f)
            .background(color = Color(red, green, blue))
    )
}

//@Preview(showSystemUi = true)
//@Composable
//fun MainPagePreview() {
//    MainPage()
//}