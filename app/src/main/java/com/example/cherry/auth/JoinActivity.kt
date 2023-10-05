package com.example.cherry.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.cherry.MainActivity
import com.example.cherry.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.content.Intent

class JoinActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        auth = Firebase.auth

        val backBtn=findViewById<Button>(R.id.button6)
        backBtn.setOnClickListener{
            finish()
        }

        //회원가입btn_press
        val joinBtn=findViewById<Button>(R.id.button7)
        joinBtn.setOnClickListener{
            val email=findViewById<EditText>(R.id.signup_Email).text.toString()
            val password=findViewById<EditText>(R.id.signup_pw).text.toString()

            //new_account
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) { task ->
                    //sucess
                    if (task.isSuccessful) {
                        Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }
                    //fail
                    else {
                        Toast.makeText(this, "회원가입 실패!: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }
}