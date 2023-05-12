package com.example.assignment3.View

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment3.Model.Book
import com.example.assignment3.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private lateinit var book: Book
private lateinit var firebaseAuth:FirebaseAuth
private var favBooks = mutableListOf<Book>()






@Composable
fun BookDetailsPage(navigateBack:() -> Unit, navigateToUserPage : () -> Unit, navigateToAdminPage: () ->Unit,  refresh : () -> Unit){
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(0.dp)
        .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var context  = LocalContext.current

        loadFavBookList(firebaseAuth, favBooks)

        var isInMyFavourite by remember {
            mutableStateOf(isFavourite())
        }


        val backIcon = painterResource(id = R.drawable.back)

        IconButton(onClick = {navigateBack()
                               isInMyFavourite = false}) {
            Icon(painter = backIcon, contentDescription = null , modifier = Modifier
                .padding(start = 10.dp, top = 10.dp)
                .width(25.dp)
                .height(25.dp))
        }

        val icon1 = painterResource(id = R.drawable.book)
        Image(painter = icon1, contentDescription = null, modifier = Modifier
            .fillMaxWidth(fraction = 0.6f)
            .padding(top = 100.dp)
        )


        Text(
            text =  if (book.title == null)"${book.volumeInfo.title}" else "${book.title}",
            fontFamily = FontFamily.SansSerif,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 15.dp)
        )

        Text(
            text = "${book.price} $",
            fontFamily = FontFamily.SansSerif,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(193,103,207),
            modifier = Modifier.padding(top = 15.dp)
        )


        Text(
            text = "${book.description}",
            fontFamily = FontFamily.Default,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .fillMaxWidth(fraction = 0.8f)
                .padding(top = 25.dp)
        )



        Text(
            text = "${isInMyFavourite}",
            fontFamily = FontFamily.Default,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .fillMaxWidth(fraction = 0.8f)
                .padding(top = 25.dp)
        )






        Button(
            onClick = {
//                isInMyFavourite = !isInMyFavourite
                checkIsFavourite(context, isInMyFavourite)
                isInMyFavourite = !isInMyFavourite
//                likeButton()
//                refresh()

            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            modifier = Modifier
                .padding(top = 250.dp)
                .height(50.dp)
                .width(300.dp),
            shape = RoundedCornerShape(10),
        ) {

            Icon(
                painter = painterResource(id =
                    if(isInMyFavourite){
                        R.drawable.heart_red
                    }else{
                        R.drawable.heart_empty
                    }
                ),
                contentDescription = null
            )

//            Text(text = "Add to Favourite", fontSize = 20.sp, fontFamily = FontFamily.SansSerif)
        }

    }
}


fun isFavourite() : Boolean{
    for(b in favBooks){
        if (b.id == book.id){
            println(b.title)
            println(book.title)
            return true
        }
    }
    return false

}


fun getBookData(selected_book:Book, firebaseAUTH: FirebaseAuth){
    book = selected_book
    firebaseAuth = firebaseAUTH

}

fun checkIsFavourite(context: Context, isInMyFavourite:Boolean){

    if(isInMyFavourite){
        Toast.makeText(context, "remove", Toast.LENGTH_SHORT)
        println("remove")
        removeFromFavourite(context)
    }else{
        Toast.makeText(context, "add", Toast.LENGTH_SHORT)
        println("add")
        addToFavourite(context)
    }
}


fun addToFavourite(context:Context){
    val timestamp = System.currentTimeMillis()

    val hashMap = HashMap<String, Any>()
    hashMap["bookId"] = book.id
    hashMap["timestamp"] = timestamp

    var ref = FirebaseDatabase.getInstance().getReference("Users")

//    Toast.makeText(context, "i am at add", Toast.LENGTH_SHORT)
    ref.child(firebaseAuth.uid!!).child("Favourites").child(book.id)
        .setValue(hashMap)
        .addOnSuccessListener {
            Toast.makeText(context, "Book added to favourite", Toast.LENGTH_SHORT)
//            book.isFavorite = true
        }
        .addOnFailureListener {
            Toast.makeText(context, "Failed to add to favourite", Toast.LENGTH_SHORT)
        }

//
//    val hashMap2: HashMap<String, Any> = HashMap()
//    hashMap2["isFavorite"] = true


//
//    val ref2 = FirebaseDatabase.getInstance().getReference("Books")
//    ref2.child(book.id)
//        .updateChildren(hashMap2)
//        .addOnSuccessListener {
////            Toast.makeText(context, "Book updated", Toast.LENGTH_SHORT).show()
//        }
//        .addOnFailureListener {e ->
////            progressDialog.dismiss()
////            Toast.makeText(context, "Failed to update the book", Toast.LENGTH_SHORT).show()
//
//        }
}


fun removeFromFavourite(context: Context){
    Toast.makeText(context, "i am at remove", Toast.LENGTH_SHORT)
    var ref = FirebaseDatabase.getInstance().getReference("Users")
    ref.child(firebaseAuth.uid!!).child("Favourites").child(book.id)
        .removeValue()
        .addOnSuccessListener {
            Toast.makeText(context, "Book removed", Toast.LENGTH_SHORT)
//            book.isFavorite = false
        }
        .addOnFailureListener {
            Toast.makeText(context, "Failed to remove", Toast.LENGTH_SHORT)
        }


//    val hashMap2: HashMap<String, Any> = HashMap()
//    hashMap2["isFavorite"] = false
//
//
//
//    val ref2 = FirebaseDatabase.getInstance().getReference("Books")
//    ref2.child(book.id)
//        .updateChildren(hashMap2)
//        .addOnSuccessListener {
////            Toast.makeText(context, "Book updated", Toast.LENGTH_SHORT).show()
//        }
//        .addOnFailureListener {e ->
////            progressDialog.dismiss()
////            Toast.makeText(context, "Failed to update the book", Toast.LENGTH_SHORT).show()
//
//        }



}