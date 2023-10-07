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
import android.util.Log
import android.widget.Toast
import com.example.cherry.auth.UserDataModel
import com.example.cherry.setting.MyPageActivity
import com.example.cherry.setting.SettingActivity
import com.example.cherry.utils.FirebaseRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var cardstackAdapter : CardStackAdapter
    lateinit var manager : CardStackLayoutManager

    private var TAG=""
    //control userdata
    private val usersDataList= mutableListOf<UserDataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //logout option
        val logout=findViewById<ImageView>(R.id.logout)
        logout.setOnClickListener{
            val auth= Firebase.auth
            auth.signOut()

            val intent_intro=Intent(this,IntroActivity::class.java)
            startActivity(intent_intro)
        }

        //setting option
        val mypage=findViewById<ImageView>(R.id.mypage)
        mypage.setOnClickListener{
            val intent_setting=Intent(this, SettingActivity::class.java)
            startActivity(intent_setting)
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

        //adapter accept
        cardstackAdapter=CardStackAdapter(baseContext,usersDataList)
        cardStackView.layoutManager=manager
        cardStackView.adapter=cardstackAdapter

        //show card in cardstackview
        getUserDataList()
    }

    private fun getUserDataList(){
        val postListener = object : ValueEventListener {
            //dataSnapshot : firebase instore data
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //show one by one(cardstackview)
                //key -> uid, value -> other data
                for (dataModel in dataSnapshot.children){
                    val user = dataModel.getValue(UserDataModel::class.java)
                    usersDataList.add(user!!)
                }
                //sync_adapter
                cardstackAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        FirebaseRef.userInfoRef.addValueEventListener(postListener)
    }
}