package com.example.cherry.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.cherry.R
import com.example.cherry.auth.IntroActivity
import com.example.cherry.message.MyLikeListActivity
import com.example.cherry.message.MyMsgActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        //go to mypage
        val mybtn=findViewById<Button>(R.id.myPageBtn)
        mybtn.setOnClickListener {
            val intent_mypage= Intent(this, MyPageActivity::class.java)
            startActivity(intent_mypage)
        }

        //go to mylike
        val mylikeBtn=findViewById<Button>(R.id.myLikeList)
        mylikeBtn.setOnClickListener {
            val intent_mylike= Intent(this, MyLikeListActivity::class.java)
            startActivity(intent_mylike)
        }

        //go to myMsg
        val myMsgBtn=findViewById<Button>(R.id.myMsg)
        myMsgBtn.setOnClickListener {
            val intent_mymsg= Intent(this, MyMsgActivity::class.java)
            startActivity(intent_mymsg)
        }

        //logout
        val logout=findViewById<Button>(R.id.logoutBtn)
        logout.setOnClickListener {
            val auth= Firebase.auth
            auth.signOut()

            val intent_intro=Intent(this,IntroActivity::class.java)
            startActivity(intent_intro)
        }
    }
}