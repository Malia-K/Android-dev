package com.example.assignment3.ViewModel

import android.app.ProgressDialog
import android.content.Context
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AuthViewModel(var firebaseAuth: FirebaseAuth, var progressDialog: ProgressDialog) {

//    lateinit var firebaseAuth : FirebaseAuth
//    lateinit var progressDialog: ProgressDialog

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
}