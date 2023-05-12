package com.example.assignment3.View

import android.app.ProgressDialog
import android.content.Context
import android.util.Patterns
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment3.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


private lateinit var firebaseAuth : FirebaseAuth
private lateinit var progressDialog: ProgressDialog

//var AuthViewModel: AuthViewModel = AuthViewModel(firebaseAuth, progressDialog)

@Composable
fun SignInPage( navigateToSignUp : () -> Unit,  navigateToUserPage : () -> Unit , navigateToAdminPage : () -> Unit){
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




        val welcomeIcon1 = painterResource(id = R.drawable.welcome_2)
        Image(painter = welcomeIcon1, contentDescription = null, modifier = Modifier
            .fillMaxWidth(fraction = 0.7f)
            .padding(top = 100.dp)
        )


        val emailValue = remember { mutableStateOf("") }
        val passwordValue = remember { mutableStateOf("") }

        OutlinedTextField(
            value = emailValue.value,
            onValueChange = { emailValue.value = it },
            placeholder = { Text(text = "Email", color = Color(142,142,147),)},
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color(142,142,147)),
            modifier = Modifier
                .fillMaxWidth(0.77f)
                .padding(top = 40.dp),


        )

        OutlinedTextField(
            value = passwordValue.value,
            onValueChange = { passwordValue.value = it},
            placeholder = { Text(text = "Password", color = Color(142,142,147))},
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
                            validateData(emailValue.value, passwordValue.value, context, navigateToUserPage, navigateToAdminPage)

                          },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(193,103,207), contentColor = Color.White),
                modifier = Modifier
                    .padding(top = 50.dp)
                    .height(50.dp)
                    .width(300.dp),
                shape = RoundedCornerShape(10),
            ) {
                Text(text = stringResource(R.string.login), fontSize = 20.sp, fontFamily = FontFamily.SansSerif)
            }


            Text(text = "Don't have an account yet?", modifier = Modifier.padding(top = 20.dp))
            TextButton(onClick = navigateToSignUp) {
                Text(text = "Sign up here", color = Color.Black, style = TextStyle(textDecoration = TextDecoration.Underline))
            }
        }




    }
}


fun validateData(email : String, password : String, context : Context, navigateToUserPage : () -> Unit, navigateToAdminPage : () -> Unit){
    if(email.isEmpty()){
        Toast.makeText(context, "Enter your email...", Toast.LENGTH_SHORT).show()
    }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        Toast.makeText(context, "Invalid email pattern...", Toast.LENGTH_SHORT).show()
    }else if (password.isEmpty()){
        Toast.makeText(context, "Enter password...", Toast.LENGTH_SHORT).show()
    }else{
        loginUser(email, password, context, navigateToUserPage, navigateToAdminPage)
    }
}

fun loginUser(email : String, password : String, context: Context, navigateToUserPage : () -> Unit, navigateToAdminPage : () -> Unit) {
    progressDialog.setMessage("Logging In...")
    progressDialog.show()

    firebaseAuth.signInWithEmailAndPassword(email, password)
        .addOnSuccessListener {
            checkUser(email, password, context, navigateToUserPage, navigateToAdminPage)
        }
        .addOnFailureListener { e->
            Toast.makeText(context, "Email or password is wrong, please enter again...", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }
}

fun checkUser(email: String, password: String, context: Context, navigateToUserPage : () -> Unit, navigateToAdminPage : () -> Unit) {
    progressDialog.setMessage("Checking User...")

    val firebaseUser = firebaseAuth.currentUser!!

    val ref = FirebaseDatabase.getInstance().getReference("Users")
    ref.child(firebaseUser.uid)
        .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                progressDialog.dismiss()

                val userType = snapshot.child("userType").value
                if (userType == "user"){
                    navigateToUserPage()
                }else if (userType == "admin"){
                    navigateToAdminPage()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

}










