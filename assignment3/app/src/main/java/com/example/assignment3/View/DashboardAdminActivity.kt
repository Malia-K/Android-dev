package com.example.assignment3.View

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.example.assignment3.R
import com.example.assignment3.Model.Book
import com.example.assignment3.data.BookshelfRepository
import com.example.assignment3.data.DefaultBookshelfRepository
import com.example.assignment3.network.BookshelfApiService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.example.assignment3.Model.BottomMenuItem
import com.example.assignment3.ViewModel.ApiViewModel


private lateinit var firebaseAuth : FirebaseAuth
private lateinit var progressDialog: ProgressDialog

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BookshelfApiService.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()


val bookshelfApiService: BookshelfApiService = retrofit.create(BookshelfApiService::class.java)
val bookshelfRepository = DefaultBookshelfRepository(bookshelfApiService)






var books = mutableListOf<Book>()

var apiBooks : List<Book>? = null


var cnt = 0
private var sortCnt = 0

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DashboardAdminPage(navigateToWelcomePage : () -> Unit,  navigateToAddBookPage : () -> Unit, navigateToUpdateBookPage: () -> Unit, navigateBack : () -> Unit, navigateToBookDetailPage : () ->Unit, refresh : () -> Unit, navigateToFavBookPage:()-> Unit){

    firebaseAuth = FirebaseAuth.getInstance()
    val scaffoldState = rememberScaffoldState()

    var context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { MyBottomBar(navigateToFavBookPage, refresh) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToAddBookPage();
                bookList()},
                modifier = Modifier
//                    .fillMaxHeight(0.7f)
                    .fillMaxWidth(0.2f),
                shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
                backgroundColor = Color(193, 103, 207),
                contentColor = Color.White,) {

                val plusSign = painterResource(id = R.drawable.plus_white)
                Icon(
                    painter = plusSign, contentDescription = null, modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                )
            }
        },
        scaffoldState = scaffoldState,
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) {
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


            val apiViewModel =  remember {
                ViewModelProvider(context as ViewModelStoreOwner).get(ApiViewModel::class.java)
            }
            apiBooks = remember {
                apiViewModel.loadBooks(bookshelfRepository, searchValue.value)
            }
//        refresh()

            Row(modifier = Modifier
                .fillMaxWidth(1f)
                .height(60.dp)
                .padding(top = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = { logout(navigateToWelcomePage)}
                ) {
                    Icon(painter = logoutIcon, contentDescription = null, modifier = Modifier.fillMaxHeight(0.5f))
                }

            Text(
                text = "What do you want to read today, Admin?",
                fontFamily = FontFamily.SansSerif,
                fontSize = 13.sp,
                color = Color(79, 79, 79)
            )


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
                        IconButton(onClick = { refresh()}) {
                            Icon(painter = searchIcon, contentDescription = null, modifier = Modifier
                                .width(18.dp)
                                .height(20.dp) )
                        }

                    },
                    value = searchValue.value,
                    onValueChange = {
                        searchValue.value = it;
                        searchFilter(searchValue.value, context);
                        loadApiBooks(context, searchValue.value)

                    },
                    placeholder = { Text(text = "Search", color = Color(142,142,147),) },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color(142,142,147)),
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .padding(top = 15.dp),
                )

                IconButton(onClick = { sort(refresh) }
                ) {
                    Icon(painter = sortIcon, contentDescription = null, modifier = Modifier
                        .padding(end = 20.dp)
                        .width(30.dp)
                        .height(30.dp))
                }


            }

            if(cnt == 0){
                bookList()
                cnt++;
            }

//                loadApiBooks(context)


            Column(modifier = Modifier.fillMaxSize()) {
                apiBooks?.let {
                    apiBookView(it, navigateToUpdateBookPage, navigateBack, navigateToBookDetailPage, context, refresh, firebaseAuth)
                }
                bookView(books, navigateToUpdateBookPage, navigateBack, navigateToBookDetailPage, context, refresh, firebaseAuth)
            }




        }
    }



}




@Composable
fun MyBottomBar(navigateToFavBookPage: () -> Unit, refresh: () -> Unit) {
    // items list
    val bottomMenuItemsList = prepareBottomMenu()

    val contextForToast = LocalContext.current.applicationContext

    var selectedItem by remember {
        mutableStateOf("Search")
    }

    BottomAppBar(
        cutoutShape = CircleShape
    ) {
        bottomMenuItemsList.forEachIndexed { index, menuItem ->
            if (index == 1) {
                // add an empty space for FAB
                BottomNavigationItem(
                    selected = false,
                    onClick = {},
                    icon = {},
                    enabled = false
                )
            }

            BottomNavigationItem(
                selected = (selectedItem == menuItem.label),
                onClick = {
                    selectedItem = menuItem.label
                    if(menuItem.route == "fav_book_page"){
                        getFavBookData(firebaseAuth)
                        navigateToFavBookPage()
                    }else if(menuItem.route == "admin_page"){
                        refresh()
                    }
                },
                icon = {
                    Icon(
                        imageVector = menuItem.icon,
                        contentDescription = menuItem.label
                    )
                },
                enabled = true
            )
        }
    }
}

private fun prepareBottomMenu(): List<BottomMenuItem> {
    val bottomMenuItemsList = arrayListOf<BottomMenuItem>()

    // add menu items
    bottomMenuItemsList.add(BottomMenuItem(label = "Search", icon = Icons.Filled.Search , route = "admin_page"))
    bottomMenuItemsList.add(BottomMenuItem(label = "Saved", icon = Icons.Filled.Bookmark, route = "fav_book_page"))

    return bottomMenuItemsList
}



fun loadApiBooks(context: Context,query : String){
    val apiViewModel = ViewModelProvider(context as ViewModelStoreOwner).get(ApiViewModel::class.java)
    apiBooks = apiViewModel.loadBooks(bookshelfRepository, query)

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun apiBookView(booksList: List<Book>, navigateToUpdateBookPage: () -> Unit, navigateBack: () -> Unit, navigateToBookDetailPage: () -> Unit, context: Context, refresh: () -> Unit, firebaseAUTH: FirebaseAuth) {
    val listState = rememberLazyListState()
//    loadApiBooks(context, )
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(7.dp),
//        modifier = Modifier.padding(top = 20.dp, start = 12.dp)
    ){
        items(booksList) { book ->
            Card(onClick = {
                getBookData(book, firebaseAUTH);
                navigateToBookDetailPage()
            }
            ){
                Row(
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth(0.9f)
                        .background(Color.White)
                        .border(width = 2.dp, color = Color(209, 209, 209))
                        .padding(start = 20.dp, end = 10.dp, bottom = 20.dp, top = 10.dp),
//                verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.50f)
                            .fillMaxHeight(1f),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        println(book.volumeInfo.title)
                        Text(text = "${book.volumeInfo.title}", fontSize = 16.sp, fontWeight = FontWeight.Bold)

                        var description_shorted = book.volumeInfo.description

                        if(book.volumeInfo.description.length > 44){
                            description_shorted = book.volumeInfo.description.subSequence(0,45) as String
                            description_shorted += "..."

                        }


                        Text(text = "${description_shorted}", fontSize = 16.sp, )
                    }



                    Column(
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.5f)
                            .fillMaxHeight(1f)
                            .fillMaxSize()
//                    verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        var expanded by remember { mutableStateOf(false) }

                        Box{

                            IconButton(onClick = { expanded = true}) {
                                val menu = painterResource(id = R.drawable.menu)
                                Image(painter = menu, contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxHeight(0.15f)
                                        .padding(start = 25.dp)
                                )
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
//                        modifier = Modifier.padding(start = 50.dp)
                            ) {
                                DropdownMenuItem(onClick = { getIdToUpdate(book.id); navigateToUpdateBookPage() }) {
                                    Text("Edit")
                                }
                                DropdownMenuItem(onClick = { deleteBookById(book.id, book.volumeInfo.title, context, navigateBack, refresh)}) {
                                    Text("Delete", color = Color.Red)
                                }

                            }
                        }


                        Text(
                            text = "${book.price} $",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(193, 103, 207),
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .weight(1f)
                        )


                    }

                }
            }



        }
    }
}


fun sort(refresh: () -> Unit){
    if(sortCnt == 1){
        sortCnt -= 1
    }else if(sortCnt == 0){
        sortCnt += 1
    }

    bookList()
    refresh()


}


fun bookList(){
    val ref = FirebaseDatabase.getInstance().reference

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
fun bookView(booksList: List<Book>, navigateToUpdateBookPage: () -> Unit, navigateBack: () -> Unit, navigateToBookDetailPage: () -> Unit, context: Context, refresh: () -> Unit, firebaseAUTH: FirebaseAuth, ){
    val listState = rememberLazyListState()
    bookList()
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(7.dp),
        modifier = Modifier.padding(top = 20.dp, start = 12.dp)
    ){
        items(booksList) { book ->
            Card(onClick = {
                    getBookData(book, firebaseAUTH);
                    navigateToBookDetailPage()
                }
            ){
                Row(
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth(0.9f)
                        .background(Color.White)
                        .border(width = 2.dp, color = Color(209, 209, 209))
                        .padding(start = 20.dp, end = 10.dp, bottom = 20.dp, top = 10.dp),
//                verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.50f)
                            .fillMaxHeight(1f),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        println(book.volumeInfo.title)
                        Text(text = "${book.title}", fontSize = 16.sp, fontWeight = FontWeight.Bold)

                        var description_shorted = book.description

                        if(book.description.length > 44){
                            description_shorted = book.description.subSequence(0,45) as String
                            description_shorted += "..."

                        }


                        Text(text = "${description_shorted}", fontSize = 16.sp, )
                    }



                    Column(
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.5f)
                            .fillMaxHeight(1f)
                            .fillMaxSize()
//                    verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        var expanded by remember { mutableStateOf(false) }

                        Box{

                            IconButton(onClick = { expanded = true}) {
                                val menu = painterResource(id = R.drawable.menu)
                                Image(painter = menu, contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxHeight(0.15f)
                                        .padding(start = 25.dp)
                                )
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
//                        modifier = Modifier.padding(start = 50.dp)
                            ) {
                                DropdownMenuItem(onClick = { getIdToUpdate(book.id); navigateToUpdateBookPage() }) {
                                    Text("Edit")
                                }
                                DropdownMenuItem(onClick = { deleteBookById(book.id, book.title, context, navigateBack, refresh)}) {
                                    Text("Delete", color = Color.Red)
                                }

                            }
                        }


                        Text(
                            text = "${book.price} $",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(193, 103, 207),
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .weight(1f)
                        )


                    }

                }
            }



        }
    }
}

fun deleteBookById(id: String, title: String, context: Context, navigateBack: () -> Unit, refresh: () -> Unit) {
    progressDialog = ProgressDialog(context)
    progressDialog.setTitle("Please wait")
    progressDialog.setMessage("Deleting the $title...")
    progressDialog.setCanceledOnTouchOutside(false)
    progressDialog.show()

    val ref = FirebaseDatabase.getInstance().getReference("Books")
    ref.child(id)
        .removeValue()
        .addOnSuccessListener {
            Toast.makeText(context, "Book deleted", Toast.LENGTH_LONG).show()
            progressDialog.dismiss()
//            loadBookList(id)
            refresh()
        }
        .addOnFailureListener {
            progressDialog.dismiss()
            Toast.makeText(context, "Failed to delete the book", Toast.LENGTH_SHORT).show()
        }
}


fun searchFilter(charSequence: CharSequence, context: Context){
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













