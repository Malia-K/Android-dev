package com.example.assignment3.View

import android.app.ProgressDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment3.R
import com.example.assignment3.Model.Book
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


private lateinit var firebaseAuth : FirebaseAuth
private lateinit var progressDialog: ProgressDialog


var userBooks = mutableListOf<Book>()



private var sortCnt = 0

@Composable
fun DashboardUserPage(navigateToWelcomePage : () -> Unit, navigateBack : () -> Unit,  navigateToBookDetailPage : () ->Unit,  refresh : () -> Unit, navigateToFavBookPage:()-> Unit){

    firebaseAuth = FirebaseAuth.getInstance()


    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(color = Color.White)
        .padding(start = 20.dp)

    ) {
        var searchIcon = painterResource(id = R.drawable.search_icon)
        var logoutIcon = painterResource(id = R.drawable.logout)
        var favBooksIcon = painterResource(id = R.drawable.favbook)
        var sortIcon = painterResource(id = R.drawable.sort)
        var context = LocalContext.current
        val searchValue = remember { mutableStateOf("") }
        var userEmail = firebaseAuth.currentUser?.email

        Row(modifier = Modifier
            .fillMaxWidth(1f)
            .height(60.dp)
            .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { logout(navigateToWelcomePage)}
            ) {
                Icon(painter = logoutIcon, contentDescription = null, modifier = Modifier.fillMaxHeight(0.5f))
            }
//            Text(
//                text = "What do you want to read today ${userEmail}?",
//                fontFamily = FontFamily.SansSerif,
//                fontSize = 14.sp,
//                color = Color(79, 79, 79)
//            )

            IconButton(onClick = { getFavBookData(firebaseAuth)
                                    navigateToFavBookPage()}
            ) {
                Icon(painter = favBooksIcon, contentDescription = null, modifier = Modifier.fillMaxHeight(0.5f))
            }


        }

        Row(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                leadingIcon = {
                    Icon(painter = searchIcon, contentDescription = null, modifier = Modifier
                        .width(18.dp)
                        .height(20.dp) )
                },
                value = searchValue.value,
                onValueChange = {
                    searchValue.value = it;
                    searchFilterUser(searchValue.value, context)
                },
                placeholder = { Text(text = "Search", color = Color(142,142,147),) },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color(142,142,147)),
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .padding(top = 15.dp),
            )

            IconButton(onClick = {sort(refresh)}
            ) {
                Icon(painter = sortIcon, contentDescription = null, modifier = Modifier
                    .padding(end = 20.dp)
                    .width(30.dp)
                    .height(30.dp))
            }


        }

        if(cnt == 0){
            getBookList()
            cnt++;
        }

        getBookView(allBooks = books, context = context, navigateToBookDetailPage)

    }

}







fun getBookList(){
    val ref = FirebaseDatabase.getInstance().reference
//    Toast.makeText(context, "before fun", Toast.LENGTH_SHORT).show()

    if(sortCnt == 0) {
        ref.child("Books").orderByChild("price").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                books.clear()
                for (bookSnapshot in snapshot.children) {
                    val book = bookSnapshot.getValue(Book::class.java)
                    book?.let {
                        books.add(it)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    else if(sortCnt == 1){
        ref.child("Books").orderByChild("price").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                books.clear()
                for (bookSnapshot in snapshot.children) {
                    val book = bookSnapshot.getValue(Book::class.java)
                    book?.let {
                        books.add(it)
                    }
                }
                books.sortByDescending { it.price }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun getBookView(allBooks: List<Book>, context : Context, navigateToBookDetailPage : () ->Unit){
//    val listState = rememberLazyListState()
//    getBookList(context)
//    Toast.makeText(context, "in fun", Toast.LENGTH_SHORT).show()
    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 12.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(7.dp)

    ) {
//        Toast.makeText(context, "before for", Toast.LENGTH_SHORT).show()
//        Toast.makeText(context, "${allBooks.size}", Toast.LENGTH_SHORT).show()
        for (book in allBooks){

//            Toast.makeText(context, "I am here", Toast.LENGTH_SHORT).show()
            Card(onClick = {
                getBookData(book, firebaseAuth);
                navigateToBookDetailPage()
            }) {
                Row(
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth(0.9f)
                        .background(Color.White)
                        .border(width = 2.dp, color = Color(209, 209, 209))
                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.50f)
                            .fillMaxHeight(1f),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {

//                    Toast.makeText(context, "${book.title}", Toast.LENGTH_SHORT).show()

                        Text(text = "${book.title}", fontSize = 16.sp, fontWeight = FontWeight.Bold)

                        var description_shorted = book.description

                        if(book.description.length > 44){
                            description_shorted = book.description.subSequence(0,45) as String
                            description_shorted += "..."

                        }


                        Text(text = "${description_shorted}", fontSize = 16.sp, )
                    }

                    Text(
                        text = "${book.price} $",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(193, 103, 207),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 35.dp)
                    )

                }
            }

        }
    }
}


fun searchFilterUser(charSequence: CharSequence, context: Context){
    var filterList = mutableListOf<Book>()

    var constraint : CharSequence = charSequence

    if(constraint != null && constraint.isNotEmpty()){
        constraint = constraint.toString().lowercase()

        for ( book in books){
            if(book.title.lowercase().contains(constraint)){
                filterList.add(book)
            }
        }
    }else{
        bookList()
    }

    books = filterList




}




