package com.tyas.smartfarm.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRepository(private val auth: FirebaseAuth) {

    // Fungsi untuk mendaftarkan pengguna baru
    suspend fun register(email: String, password: String): FirebaseUser? {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        return result.user
    }

    // Fungsi untuk login pengguna
    suspend fun login(email: String, password: String): FirebaseUser? {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        return result.user
    }

    // Fungsi untuk mendapatkan pengguna saat ini
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    // Fungsi untuk logout pengguna
    fun logout() {
        auth.signOut()
    }
}
