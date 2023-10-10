package com.example.cherry.utils

import com.google.firebase.auth.FirebaseAuth

class FirebaseUtils {
    companion object{
        private var auth = FirebaseAuth.getInstance()
        fun getUid():String{
            return auth.currentUser?.uid.toString()
        }
    }
}