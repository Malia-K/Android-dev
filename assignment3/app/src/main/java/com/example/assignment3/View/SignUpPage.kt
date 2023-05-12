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
import com.google.firebase.database.FirebaseDatabase


private lateinit var firebaseAuth : FirebaseAuth
private lateinit var progressDialog: ProgressDialog

@Composable
fun SignUpPage( navigateToSignIn : () -> Unit, navigateToMainPage : () -> Unit){
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

        val welcomeIcon1 = painterResource(id = R.drawable.welcome_3)
        Image(painter = welcomeIcon1, contentDescription = null, modifier = Modifier
            .fillMaxWidth(fraction = 0.7f)
            .padding(top = 100.dp)
        )
        val emailValue = remember { mutableStateOf("") }
        val passwordValue = remember { mutableStateOf("") }
        val passwordConfirmValue = remember { mutableStateOf("") }

        OutlinedTextField(
            value = emailValue.value,
            onValueChange = { emailValue.value = it },
            placeholder = { Text(text = stringResource(R.string.email), color = Color(142,142,147),) },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color(142,142,147)),
            modifier = Modifier
                .fillMaxWidth(0.77f)
                .padding(top = 40.dp)

        )

        OutlinedTextField(
            value = passwordValue.value,
            onValueChange = { passwordValue.value = it },
            placeholder = { Text(text = stringResource(R.string.password), color = Color(142,142,147)) },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color(142,142,147)),
            modifier = Modifier
                .fillMaxWidth(0.77f)
                .padding(top = 20.dp)
        )

        OutlinedTextField(
            value = passwordConfirmValue.value,
            onValueChange = { passwordConfirmValue.value = it },
            placeholder = { Text(text = stringResource(R.string.confirm_password), color = Color(142,142,147)) },
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
                            register(emailValue.value, passwordValue.value, passwordConfirmValue.value, context, navigateToSignIn)
//                            navigateToMainPage()
                          },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(193,103,207), contentColor = Color.White),
                modifier = Modifier
                    .padding(top = 50.dp)
                    .height(50.dp)
                    .width(300.dp),
                shape = RoundedCornerShape(10),
            ) {
                Text(text = stringResource(R.string.create_account), fontSize = 20.sp, fontFamily = FontFamily.SansSerif)
            }


            Text(text = stringResource(R.string.have_an_account), modifier = Modifier.padding(top = 20.dp))
            TextButton(onClick = navigateToSignIn) {
                Text(text = stringResource(R.string.sign_in_here), color = Color.Black, style = TextStyle(textDecoration = TextDecoration.Underline))
            }
        }

    }
}


fun register(email: String, password: String, confirmPass: String, context : Context,  navigateToSignIn : () -> Unit){
    validateData(email, password, confirmPass, context, navigateToSignIn)
}



fun validateData(email: String, password: String, confirmPass: String, context: Context,  navigateToSignIn : () -> Unit){
    if(email.isEmpty()){
        Toast.makeText(context, "Enter your email...", Toast.LENGTH_SHORT).show()
    }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        Toast.makeText(context, "Invalid email pattern...", Toast.LENGTH_SHORT).show()
    }else if (password.isEmpty()){
        Toast.makeText(context, "Enter password...", Toast.LENGTH_SHORT).show()
    }else if (confirmPass.isEmpty()){
        Toast.makeText(context, "Confirm password...", Toast.LENGTH_SHORT).show()
    }else if(password != confirmPass){
        Toast.makeText(context, "Password doesn't match...", Toast.LENGTH_SHORT).show()
    }else{
        createUserAccount(email, password, context,navigateToSignIn)
    }
}


fun createUserAccount(email: String, password: String, context: Context,  navigateToSignIn : () -> Unit){
    progressDialog.setMessage("Creating account...")
    progressDialog.show()

    firebaseAuth.createUserWithEmailAndPassword(email, password)
        .addOnSuccessListener {
            updateUserInfo(email, password, context,navigateToSignIn)
        }
        .addOnFailureListener { e ->
            progressDialog.dismiss()
//            Toast.makeText(this, "Failed creating account due to ${e.message}", Toast.LENGTH_SHORT).show()
        }
}

fun updateUserInfo(email: String, password: String, context: Context,  navigateToSignIn : () -> Unit) {
    progressDialog.setMessage("Saving user info...")

    val timestamp = System.currentTimeMillis()
    val uid = firebaseAuth.uid

    val hashMap: HashMap<String, Any?> = HashMap()
    hashMap["uid"] = uid
    hashMap["email"] = email
    hashMap["userType"] = "user"
    hashMap["timestamp"] = timestamp


    val ref = FirebaseDatabase.getInstance().getReference("Users")
    ref.child(uid!!)
        .setValue(hashMap)
        .addOnSuccessListener {
            Toast.makeText(context ,"Account created", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
            navigateToSignIn()

        }
        .addOnFailureListener {e->
            progressDialog.dismiss()
            Toast.makeText(context, "Failed saving user info due to ${e.message} ", Toast.LENGTH_SHORT).show()
        }



}




