package com.example.instagramapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.instagramapp.ui.theme.InstagramAppTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstagramAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    FirstPage()
//                    SecondPage()
//                    ThirdPage()
                }
            }
        }
    }
}

@Composable
fun MainHeader(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(width = 1.dp, color = Color(196, 196, 196), shape = RectangleShape),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
    ){
        val logo = painterResource(id = R.drawable.instagram_logo_svg)

        Image(
            painter = logo,
            contentDescription = null )


        Row(
            modifier = Modifier
                .fillMaxWidth(0.5f),
                horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val adding = painterResource(id = R.drawable.plus)
            val notification = painterResource(id = R.drawable.notification)
            val direct = painterResource(id = R.drawable.direct)
            Image(
                painter = adding,
                contentDescription = null,
                modifier = Modifier
                    .width(25.dp)
                    .clickable { println("adding button Clicked!") })
            Image(
                painter = notification,
                contentDescription = null,
                modifier = Modifier
                    .width(28.dp)
                    .clickable { println("notification button Clicked!") })
            Image(
                painter = direct,
                contentDescription = null,
                modifier = Modifier
                    .width(25.dp)
                    .clickable { println("direct button Clicked!") })
        }

    }

}

@Composable
fun SecondHeader(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(width = 1.dp, color = Color(196, 196, 196), shape = RectangleShape),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = stringResource(id = R.string.Nickname_), fontSize = 20.sp,fontWeight = FontWeight.Bold)

        Row(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .padding(end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween

        ) {
            val adding = painterResource(id = R.drawable.plus)
            val menuTab = painterResource(id = R.drawable.menu)

            Image(
                painter = adding,
                contentDescription = null,
                modifier = Modifier
                    .width(25.dp)
                    .clickable { println("adding button Clicked!") })

            Image(
                painter = menuTab,
                contentDescription = null,
                modifier = Modifier
                    .width(28.dp)
                    .clickable { println("notification button Clicked!") })

        }

    }

}

@Composable
fun Stories(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .border(width = 1.dp, color = Color(196, 196, 196), shape = RectangleShape)
            .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
    ) {
        Story(stringResource(R.string.urStory))
        repeat(8){
            Story(stringResource(R.string.underStories))
        }
    }
}

@Composable
fun Story(name : String){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier
            .padding(4.dp)
            .fillMaxSize(fraction = 0.8f)
            .aspectRatio(1f)
            .background(color = Color(196, 196, 196), shape = CircleShape)
        ){
        }
        Text(text = name, fontSize = 14.sp)
    }

}

@Composable
fun Content(){

    Column (
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
    ){
        Stories()
        repeat(10){
            Post()
        }

    }
}

@Composable
fun Post(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(700.dp)
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f),
                    verticalAlignment = Alignment.CenterVertically
            ){
                Box(modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize(fraction = 0.15f)
                    .aspectRatio(1f)
                    .background(color = Color(196, 196, 196), shape = CircleShape)
                ){

                }
                Text(text = stringResource(R.string.Nickname_), fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .background(color = Color(196, 196, 196), shape = RectangleShape)
            ){
                //image
            }
            Text(text = stringResource(R.string.Nickname_), Modifier.padding(start = 10.dp, top = 10.dp), fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(text = stringResource(R.string.Lorem), Modifier.padding(start = 10.dp, top = 10.dp), fontSize = 14.sp)

        }
    }
}

@Composable
fun UserInfo(){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Box(modifier = Modifier
                .padding(4.dp)
                .fillMaxSize(fraction = 0.2f)
                .aspectRatio(1f)
                .background(color = Color(196, 196, 196), shape = CircleShape)
            ){
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.85f),
                    horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "??", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                    Text(text = stringResource(R.string.Publication), fontSize = 12.sp)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "??", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                    Text(text = stringResource(R.string.Subscribers), fontSize = 12.sp)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "??", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                    Text(text = stringResource(R.string.Subscribes), fontSize = 12.sp)
                }
            }

        }
        Column(modifier = Modifier.padding(start = 20.dp, top = 5.dp, end = 40.dp)) {
            Text(text = stringResource(R.string.BioName), fontSize = 14.sp, fontStyle = FontStyle.Italic)
            Text(text = stringResource(R.string.BioInfo), fontSize = 14.sp)
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val interestingPeople = painterResource(id = R.drawable.invite)
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.35f)
                    .height(40.dp)
                    ,colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(196, 196, 196),
                        contentColor = Color.Black
                    )
            ) {
                Text(text = stringResource(R.string.Change), fontSize = 12.sp)
            }

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.7f)
                    .height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(196, 196, 196),
                    contentColor = Color.Black
                )
            ) {
                Text(text = stringResource(R.string.SendProfile), fontSize = 10.sp)
            }

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                ,colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(196, 196, 196),
                    contentColor = Color.Black
                )
            ) {
                Image(painter = interestingPeople, contentDescription = null)

            }
        }
        
        Row(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .height(100.dp)
                .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(3){
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxSize(fraction = 0.8f)
                            .aspectRatio(1f)
                            .background(color = Color(196, 196, 196), shape = CircleShape)
                    ) {
                    }
                    Text(text = stringResource(R.string.Actual), fontSize = 12.sp)
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxSize(fraction = 0.8f)
                        .aspectRatio(1f)
                        .border(width = 2.dp, color = Color(196, 196, 196), shape = CircleShape)
                        ,contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.plus), fontSize = 40.sp, fontWeight = FontWeight.Light)
                }
                Text(text = stringResource(R.string.Add), fontSize = 12.sp)
            }
        }
    }

}

@Composable
fun UserPosts(){
    Row(modifier = Modifier
        .padding(top = 12.dp, bottom = 12.dp)
        .fillMaxWidth()
        .height(25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly) {
        val grid = painterResource(id = R.drawable.grid)
        val people = painterResource(id = R.drawable.avatar)

        Image(painter = grid, contentDescription = null)
        Spacer(modifier = Modifier)
        Image(painter = people, contentDescription = null)
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp),
        content = {
            items(35){
                Box(
                    modifier = Modifier
                        .padding(1.dp)
                        .aspectRatio(1f)
                        .background(color = Color(196, 196, 196))
                )
            }

        }
    )
}

@Composable
fun SearchTab(){
    val searchIcon = painterResource(id = R.drawable.search_interface_symbol)
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp), contentAlignment = Alignment.Center ){
        Row(modifier = Modifier
            .fillMaxWidth(fraction = 0.9f)
            .height(45.dp)
            .clip(shape = RoundedCornerShape(4.dp))
            .background(color = Color(196, 196, 196)),
            verticalAlignment = Alignment.CenterVertically,
            
        ){
            Image(painter = searchIcon, contentDescription = null ,
                Modifier
                    .padding(start = 20.dp)
                    .width(25.dp))
            Text(text = stringResource(R.string.Search), fontSize = 16.sp, color = Color(97,97,97), modifier = Modifier.padding(start = 10.dp))
        }
    }


}

@Composable
fun GridContent(){
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp),

        content = {
            items(100){
                Box(
                    modifier = Modifier
                        .padding(1.dp)
                        .aspectRatio(1f)
                        .background(color = Color(196, 196, 196))
                )
            }

        }
    )
}



@Composable
fun FirstPage(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(253, 253, 253))
    ) {
        MainHeader()
        Content()
    }
}

@Composable
fun SecondPage(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(253, 253, 253))
    ) {
        SecondHeader()
        UserInfo()
        UserPosts()
    }
}


@Composable
fun ThirdPage(){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(253, 253, 253))
    ){
        SearchTab()
        GridContent()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    InstagramAppTheme {
        FirstPage()
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AnotherPreview(){
    InstagramAppTheme {
        SecondPage()
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AnotherAnotherPreview(){
    InstagramAppTheme {
        ThirdPage()
    }
}

