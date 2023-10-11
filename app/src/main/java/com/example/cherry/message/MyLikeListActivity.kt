package com.example.cherry.message

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.cherry.MainActivity
import com.example.cherry.R
import com.example.cherry.auth.UserDataModel
import com.example.cherry.setting.SettingActivity
import com.example.cherry.utils.FirebaseRef
import com.example.cherry.utils.FirebaseUtils
import com.example.cherry.utils.MyInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

//matching list
class MyLikeListActivity : AppCompatActivity() {
    private val uid=FirebaseUtils.getUid()

    //user's like to other user's uid
    private val likeUserListUid= mutableListOf<String>()
    //user's like to other user
    private val likeUserList= mutableListOf<UserDataModel>()

    lateinit var listViewAdapter : ListViewAdapter

    //reciever's uid
    lateinit var getterUid : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_like_list)

        val userListView=findViewById<ListView>(R.id.userListView)

        //connect adapter to listview
        listViewAdapter=ListViewAdapter(this,likeUserList)
        userListView.adapter=listViewAdapter

        //person who i like
        getMyLikeList()
        /*
        //if click textview, check matching
        userListView.setOnItemClickListener { parent,view,position,id ->
            checkMatching(likeUserList[position].uid.toString())
        }

        */

        //listview longclick
        userListView.setOnItemLongClickListener { parent,view,position,id ->
            checkMatching(likeUserList[position].uid.toString())
            getterUid = likeUserList[position].uid.toString()

            return@setOnItemLongClickListener(true)
        }
        //sort by name
        val sortBtnByName = findViewById<Button>(R.id.sortbyname)
        sortBtnByName.setOnClickListener{
            sortbyname()
        }

        //sort by age
        val sortBtnByAge = findViewById<Button>(R.id.sortbyage)
        sortBtnByAge.setOnClickListener{
            sortbyage()
        }

        //back
        val backBtn = findViewById<Button>(R.id.back)
        backBtn.setOnClickListener{
            val intent_setting = Intent(this, SettingActivity::class.java)
            startActivity(intent_setting)
        }
    }

    //sort by name
    private fun sortbyname(){
        likeUserList.sortBy{it.name}
        listViewAdapter.notifyDataSetChanged()
    }

    //sort by age
    private fun sortbyage(){
        likeUserList.sortBy{it.age}
        listViewAdapter.notifyDataSetChanged()
    }

    private fun checkMatching(otherUid : String){
        val postListener = object : ValueEventListener {
            //dataSnapshot : firebase instore data
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //key -> uid, value -> other data

                //if liker is empty
                if(dataSnapshot.children.count() == 0){
                    Toast.makeText(this@MyLikeListActivity, "상대방이 좋아한 사용자가 없습니다!", Toast.LENGTH_SHORT).show()
                }
                //if liker hava data
                else {
                    var check_matching = false
                    for (dataModel in dataSnapshot.children) {
                        //check matching
                        if(dataModel.key.toString().equals(uid))
                            check_matching=true
                    }

                    //make matching message by result
                    if(check_matching){
                        Toast.makeText(this@MyLikeListActivity, "매칭된 상대입니다!", Toast.LENGTH_SHORT)
                            .show()

                        //dialog
                        showDialog()
                    }
                    else{
                        Toast.makeText(
                            this@MyLikeListActivity,
                            "매칭된 상대가 아닙니다!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        FirebaseRef.userLikeRef.child(otherUid).addValueEventListener(postListener)
    }

    //person who i like
    private fun getMyLikeList(){
        val postListener = object : ValueEventListener {
            //dataSnapshot : firebase instore data
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataModel in dataSnapshot.children){
                    likeUserListUid.add(dataModel.key.toString())
                }

                //get all user's data
                getUserDataList()
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        FirebaseRef.userLikeRef.child(uid).addValueEventListener(postListener)
    }

    //get all user's data
    private fun getUserDataList(){
        val postListener = object : ValueEventListener {
            //dataSnapshot : firebase instore data
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataModel in dataSnapshot.children){
                    val user = dataModel.getValue(UserDataModel::class.java)

                    //we can know person who user like
                    if(likeUserListUid.contains(user?.uid)){
                        likeUserList.add(user!!)
                    }
                    //notify to listview that info is changed
                    listViewAdapter.notifyDataSetChanged()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        //get resource from
        FirebaseRef.userInfoRef.addValueEventListener(postListener)
    }

    //Dialog
    private fun showDialog(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog,null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("메시지 보내기")

        val mAlertDialog = mBuilder.show()

        val btn = mAlertDialog.findViewById<Button>(R.id.sendBtnArea)
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