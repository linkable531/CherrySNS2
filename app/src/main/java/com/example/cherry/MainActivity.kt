package com.example.cherry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.cherry.auth.IntroActivity
import com.example.cherry.slider.CardStackAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction
import android.content.Intent
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var cardstackAdapter : CardStackAdapter
    lateinit var manager : CardStackLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //logout option
        val logout=findViewById<ImageView>(R.id.logout)
        logout.setOnClickListener{
            val auth= Firebase.auth
            auth.signOut()

            val intent=Intent(this,IntroActivity::class.java)
            startActivity(intent)
        }

        //cardstackview
        val cardStackView = findViewById<CardStackView>(R.id.cardStackView)

        manager = CardStackLayoutManager(baseContext, object : CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {

            }

            override fun onCardSwiped(direction: Direction?) {

            }

            override fun onCardRewound() {

            }

            override fun onCardCanceled() {

            }

            override fun onCardAppeared(view: View?, position: Int) {

            }

            override fun onCardDisappeared(view: View?, position: Int) {

            }
        })
        
        //test case
        val testList = mutableListOf<String>()
        testList.add("a")
        testList.add("b")
        testList.add("c")

        //adapter accept
        cardstackAdapter=CardStackAdapter(baseContext,testList)
        cardStackView.layoutManager=manager
        cardStackView.adapter=cardstackAdapter
    }
}