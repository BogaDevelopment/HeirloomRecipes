package com.bogadevelopment.heirloomrecipes.core.auth.datasource

import android.util.Log
import com.bogadevelopment.heirloomrecipes.core.auth.model.UserSession
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseAuthDataSource {

    // Instancia única
    private val auth: FirebaseAuth = Firebase.auth

    init {
        Log.d("DEBUG_AUTH", "✅ FirebaseAuthDataSource initialized")
    }

    suspend fun login(email: String, password: String): UserSession? {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        val user = result.user
        Log.d("DEBUG_AUTH", "🔐 Login successful: ${user?.uid ?: "null"}")
        return user?.let { UserSession(it.uid, it.email ?: "") }
    }

    suspend fun register(email: String, password: String): UserSession? {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val user = result.user
        Log.d("DEBUG_AUTH", "👤 Registered: ${user?.uid ?: "null"}")
        return user?.let { UserSession(it.uid, it.email ?: "") }
    }

    fun getCurrentUser(): UserSession? {
        val user = auth.currentUser
        Log.d("DEBUG_AUTH", "📄 getCurrentUser -> ${user?.uid ?: "null"}")
        return user?.let { UserSession(it.uid, it.email ?: "") }
    }

    fun observeAuthState(): Flow<UserSession?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            Log.d("DEBUG_AUTH", "⚡ Auth state changed: ${firebaseUser?.uid ?: "null"}")
            trySend(firebaseUser?.let { UserSession(it.uid, it.email ?: "") })
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    fun logout() {
        auth.signOut()
        Log.d("DEBUG_AUTH", "🚪 Signed out")
    }
}
