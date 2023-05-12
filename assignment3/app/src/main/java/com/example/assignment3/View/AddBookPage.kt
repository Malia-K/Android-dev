package com.example.assignment3.View

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment3.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


private lateinit var firebaseAuth : FirebaseAuth
private lateinit var progressDialog: ProgressDialog


@Composable
fun AddBookPage(navigateBack : () -> Unit){
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(0.dp)
        .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var context  = LocalContext.current
        val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        val backIcon = painterResource(id = R.drawable.back)

        IconButton(onClick = {navigateBack()}) {
            Icon(painter = backIcon, contentDescription = null , modifier = Modifier.padding(start = 10.dp, top = 10.dp).width(25.dp).height(25.dp))
        }

        val icon1 = painterResource(id = R.drawable.add_book)
        Image(painter = icon1, contentDescription = null, modifier = Modifier
            .fillMaxWidth(fraction = 0.7f)
            .padding(top = 100.dp)
        )
        val bookTitleValue = remember { mutableStateOf("") }
        val desctiptionValue = remember { mutableStateOf("") }
        val costValue = remember { mutableStateOf("") }

        OutlinedTextField(
            value = bookTitleValue.value,
            onValueChange = { bookTitleValue.value = it },
            placeholder = { Text(text = "Book Title", color = Color(142,142,147),) },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color(142,142,147)),
            modifier = Modifier
                .fillMaxWidth(0.77f)
                .padding(top = 40.dp)

        )

        OutlinedTextField(
            value = desctiptionValue.value,
            onValueChange = { desctiptionValue.value = it },
            placeholder = { Text(text = "Book description", color = Color(142,142,147)) },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color(142,142,147)),
            modifier = Modifier
                .fillMaxWidth(0.77f)
                .padding(top = 20.dp)
        )

        OutlinedTextField(
            value = costValue.value,
            onValueChange = { costValue.value = it },
            placeholder = { Text(text = "Price", color = Color(142,142,147)) },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color(142,142,147)),
            modifier = Modifier
                .fillMaxWidth(0.77f)
                .padding(top = 20.dp)
        )


        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                            validateBookData(bookTitleValue.value, desctiptionValue.value, costValue.value, context, navigateBack)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(193,103,207), contentColor = Color.White),
                modifier = Modifier
                    .padding(top = 50.dp)
                    .height(50.dp)
                    .width(300.dp),
                shape = RoundedCornerShape(10),
            ) {
                Text(text = "Add Book", fontSize = 20.sp, fontFamily = FontFamily.SansSerif)
            }


        }

    }
}


fun validateBookData(title: String,description : String, cost: String, context: Context, navigateBack : () -> Unit){
    if(title.isEmpty()){
        Toast.makeText(context, "Enter the title of the book...", Toast.LENGTH_SHORT).show()
    }else if (description.isEmpty()){
        Toast.makeText(context, "Enter description...", Toast.LENGTH_SHORT).show()
    }else if (cost.isEmpty()){
        Toast.makeText(context, "Enter the price...", Toast.LENGTH_SHORT).show()
    }else{
        addBook(title, description, cost, context, navigateBack)
    }
}


fun addBook(title: String,description : String, cost: String, context: Context, navigateBack : () -> Unit){
    progressDialog.setMessage("Uploading a new Book...")
    progressDialog.show()


    val timestamp = System.currentTimeMillis()
    val uid = firebaseAuth.uid


    val hashMap: HashMap<String, Any> = HashMap()
    hashMap["uid"] = "$uid"
    hashMap["id"] = "$timestamp"
    hashMap["title"] = "$title"
    hashMap["description"] = "$description"
    hashMap["price"] = "$cost"
    hashMap["timestamp"] = timestamp
    hashMap["isFavorite"] = false



    val ref = FirebaseDatabase.getInstance().getReference("Books")
    ref.child("$timestamp")
        .setValue(hashMap)
        .addOnSuccessListener {
            Toast.makeText(context, "Book added", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
            navigateBack()
        }
        .addOnFailureListener {e ->
            progressDialog.dismiss()
            Toast.makeText(context, "Failed to upload the book", Toast.LENGTH_SHORT).show()

        }

    val books = ref.get()


}



//
//@Preview
//@Composable
//fun addbookprev(){
//    AddBookPage()
//}