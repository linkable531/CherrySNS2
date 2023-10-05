package com.example.cherry.auth

import android.content.Intent
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

class IntroActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val joinBtn=findViewById<Button>(R.id.signin)
        joinBtn.setOnClickListener{
            val intent_join= Intent(this,JoinActivity::class.java)
            startActivity(intent_join)
        }

        val loginBtn=findViewById<Button>(R.id.button2)
        loginBtn.setOnClickListener{
            val id: String =findViewById<EditText>(R.id.editID).text.toString()
            //비밀번호를 프런트에서 가져온다
            val password : String =findViewById<EditText>(R.id.ediPassword).text.toString()

            if(id.isEmpty() && password.isEmpty()){
                Toast.makeText(this, "회원정보를 적어주세요!", Toast.LENGTH_SHORT).show()
            }
            //아이디만 안 눌렸을 때
            else if(id.isEmpty()){
                Toast.makeText(this, "아이디를 적어주세요!", Toast.LENGTH_SHORT).show()
            }
            //비밀번호만 안 눌렸을 때
            else if(password.isEmpty()){
                Toast.makeText(this, "비밀번호를 적어주세요!", Toast.LENGTH_SHORT).show()
            }
            //전부 눌렸다 -> 로그인
            else{
                auth= Firebase.auth

                auth.signInWithEmailAndPassword(id, password)
                    .addOnCompleteListener(this) { task ->
                        //login success
                        if (task.isSuccessful) {
                            Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()

                            val intent_main= Intent(this, MainActivity::class.java)
                            startActivity(intent_main)
                        }
                        //login fail
                        else {
                            Toast.makeText(this, "로그인 실패!: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }


            }
        }
    }
}