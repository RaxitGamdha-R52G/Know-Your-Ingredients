package com.kyi.knowyouringredients.core.firebase

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

object FirebaseAuthProvider {
    val auth: FirebaseAuth = Firebase.auth
}