package com.example.cherry.utils

import com.google.firebase.auth.FirebaseAuth

class FirebaseUtils {
    companion object{
        private lateinit var auth : FirebaseAuth
        fun getUid():String{
            auth = FirebaseAuth.getInstance()
            return auth.currentUser?.uid.toString()
        }
    }
}