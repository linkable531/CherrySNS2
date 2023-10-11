package com.example.cherry.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.cherry.R
import com.example.cherry.auth.UserDataModel
import com.example.cherry.utils.FirebaseRef
import com.example.cherry.utils.FirebaseUtils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MyMsgActivity : AppCompatActivity() {
    lateinit var listViewAdapter : MsgAdapter
    val msgList = mutableListOf<MsgModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_msg)

        //connect with listview
        val listview = findViewById<ListView>(R.id.msgListView)

        listViewAdapter=MsgAdapter(this,msgList)
        listview.adapter=listViewAdapter

        //get msg
        getMyMsg()
    }

    //get my msg from firebase
    private fun getMyMsg(){
        val postListener = object : ValueEventListener {
            //dataSnapshot : firebase instore data
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //for clear watch
                msgList.clear()

                for (dataModel in dataSnapshot.children){
                    val msg = dataModel.getValue(MsgModel::class.java)
                    msgList.add(msg!!)
                }
                listViewAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        FirebaseRef.userMsgRef.child(FirebaseUtils.getUid()).addValueEventListener(postListener)
    }
}