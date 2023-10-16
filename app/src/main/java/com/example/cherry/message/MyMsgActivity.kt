package com.example.cherry.message

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import com.example.cherry.R
import com.example.cherry.auth.UserDataModel
import com.example.cherry.utils.FirebaseRef
import com.example.cherry.utils.FirebaseUtils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.cherry.MainActivity
import com.example.cherry.utils.MyInfo
import com.example.cherry.message.MyLikeListActivity

class MyMsgActivity : AppCompatActivity() {
    lateinit var listViewAdapter : MsgAdapter
    val msgList = mutableListOf<MsgModel>()
    lateinit var getterUid : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_msg)

        //connect with listview
        val listview = findViewById<ListView>(R.id.msgListView)

        listViewAdapter=MsgAdapter(this,msgList)
        listview.adapter=listViewAdapter

        //get msg
        getMyMsg()

        //search
        val searchBtn = findViewById<ImageView>(R.id.search_button)
        searchBtn.setOnClickListener{
            searchUser()
        }

        //back
        val backBtn=findViewById<ImageView>(R.id.back_my_msg)
        backBtn.setOnClickListener{
            val intent_main = Intent(this, MainActivity::class.java)
            startActivity(intent_main)
        }
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

    //search user
    private fun searchUser() {
        //input
        val searchEditText = findViewById<EditText>(R.id.search_EditText)

        val msgBuilder = AlertDialog.Builder(this)
            .setPositiveButton("확인") { dialogInterface, i -> }
        val targetName : String = searchEditText.text.toString()

        val index : Int? = msgList.binarySearch { String.CASE_INSENSITIVE_ORDER.compare(it.senderInfo, targetName) }

        //cant find
        if(index == -1) {
            msgBuilder.setTitle("검색 오류")
            msgBuilder.setMessage("이름을 찾을 수 없습니다")
        }
        //find
        else {
            msgBuilder.setTitle("검색 성공")
            msgBuilder.setMessage(targetName + "님을 찾았습니다")
            msgBuilder.setPositiveButton("메시지 보내기", DialogInterface.OnClickListener(){ dialogInterface, i ->
                showDialog()
            })
        }

        //dialog
        val msgDlg: AlertDialog = msgBuilder.create()
        msgDlg.show()
    }

    //Dialog
    private fun showDialog(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog,null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("메시지 보내기")

        val mAlertDialog = mBuilder.show()

        val btn = mAlertDialog.findViewById<ImageView>(R.id.sendBtnArea)
        val textArea = mAlertDialog.findViewById<EditText>(R.id.sendTextArea)
        btn?.setOnClickListener {
            val mgsModel = MsgModel(MyInfo.myNickname,textArea!!.text.toString())

            //send msginfo to firebase
            FirebaseRef.userMsgRef.child(getterUid).push().setValue(mgsModel)

            //off dialog
            mAlertDialog.dismiss()
        }
    }
}