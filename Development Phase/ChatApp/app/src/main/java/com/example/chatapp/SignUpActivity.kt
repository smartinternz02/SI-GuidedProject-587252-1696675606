package com.example.chatapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var signin: TextView
    private lateinit var signup: Button
    private lateinit var auth: FirebaseAutha
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);
        signin = findViewById(R.id.signin);
        auth = FirebaseAuth.getInstance();

        signin.setOnClickListener{
            val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
            startActivity(intent)
        }

        signup.setOnClickListener{
            val name = name.text.toString()
            val email = email.text.toString()
            val password = password.text.toString()
            if (email.isEmpty() || email.isBlank() || password.isEmpty() || password.isBlank() || name.isEmpty() || name.isBlank()){
                Toast.makeText(this,"Please enter name, email and password", Toast.LENGTH_SHORT).show()
            }else if(name.length <= 3){
                Toast.makeText(this,"Enter a name of atleast 3 characters", Toast.LENGTH_SHORT).show()
            }else{
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this){task ->
                        if(task.isSuccessful){
                            addUserToDatabase(name, email, auth.currentUser?.uid!!)
                            Log.d("SignUpActivity","createUserWithEmail:Success")
                            Toast.makeText(this,"Success Fully", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@SignUpActivity, SignUpActivity::class.java)
                            startActivity(intent)
                        }else{
                            Log.w("SignUpActivity", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(this,"Authentication failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    private fun addUserToDatabase(name:String, email:String, uid:String){
        dbRef = FirebaseDatabase.getInstance().getReference()
        dbRef.child("user").child(uid).setValue(User(name, email, uid))
    }
}