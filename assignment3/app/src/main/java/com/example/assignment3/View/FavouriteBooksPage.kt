package com.example.assignment3.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.assignment3.Model.Book
import com.example.assignment3.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


var favouriteBooks = mutableListOf<Book>()
private lateinit var firebaseAuth: FirebaseAuth

@Composable
fun FavouriteBooksPage(navigateBack: ()-> Unit, navigateToUpdateBookPage:()-> Unit, navigateToBookDetailPage:()-> Unit, refresh:()-> Unit ){
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(0.dp)
        .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var context = LocalContext.current

        val backIcon = painterResource(id = R.drawable.back)

        IconButton(onClick = { navigateBack() }) {
            Icon(
                painter = backIcon, contentDescription = null, modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp)
                    .width(25.dp)
                    .height(25.dp)
            )
        }


        loadFavBookList(firebaseAuth, favouriteBooks)


        bookView(
            booksList = favouriteBooks,
            navigateToUpdateBookPage = navigateToUpdateBookPage,
            navigateBack = navigateBack,
            navigateToBookDetailPage = navigateToBookDetailPage,
            context = context,
            refresh = refresh,
            firebaseAUTH = firebaseAuth
        )
    }
}


fun loadFavBookList(firebaseAuth: FirebaseAuth, favBooks: MutableList<Book>){
    val ref = FirebaseDatabase.getInstance().getReference("Users")
    ref.child(firebaseAuth.uid!!).child("Favourites").
            addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    favBooks.clear()
                    for(ds in snapshot.children){
                        val bookId = "${ds.child("bookId").value}"
                        println(bookId)
                        var model = loadBookDetails(bookId)
//                        model.id = bookId
                        println("${model.title}, ${model.id}")
                        favBooks.add(model)


                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })


}




fun loadBookDetails(bookId: String):Book{
    var model:Book = Book()
    val ref = FirebaseDatabase.getInstance().getReference("Books")
    ref.child(bookId)
        .addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val description = "${snapshot.child("description").value}"
                val price = "${snapshot.child("price").value}"
                val timestamp = snapshot.child("timestamp").value as? Long
                val title = "${snapshot.child("title").value}"
                val uid = "${snapshot.child("uid").value}"


                model.id = bookId
                model.description = description
                model.title = title
                model.price = price
                model.uid = uid

                if( timestamp != null){
                    model.timestamp = timestamp
                }else{
                    model.timestamp = null
                }
                println("${model.title}, ${model.id}")

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    return model
}


fun getFavBookData(firebaseAUTH: FirebaseAuth){
    firebaseAuth = firebaseAUTH

}