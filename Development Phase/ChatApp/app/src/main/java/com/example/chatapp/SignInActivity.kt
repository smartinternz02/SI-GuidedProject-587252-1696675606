package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var signin: Button
    private lateinit var signup: TextView
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        auth = FirebaseAuth.getInstance()

        signup.setOnClickListener{
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        signin.setOnClickListener{
            if (email.text.isEmpty() || email.text.isBlank() || password.text.isEmpty() || password.text.isBlank()){
                Toast.makeText(this,"Please enter email and password",Toast.LENGTH_SHORT).show()
            }else{
                auth.signInWithEmailAndPassword(email.text.toString(),password.text.toString())
                    .addOnCompleteListener(this){task ->
                        if(task.isSuccessful){
                            Toast.makeText(baseContext, "Authentication success", Toast.LENGTH_SHORT).show()
                            Log.d("SignInActivity", "signinWithEmail success")
//                            val intent = Intent(appli, MainActivity::class.java)
//                            startActivity(intent)
                            Intent(applicationContext, MainActivity::class.java).also {
                                startActivity(it)
                            }

                        }else{
                            Log.w("Signinfails", "signinwithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }
    }
}