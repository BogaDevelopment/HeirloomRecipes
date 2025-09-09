package com.bogadevelopment.heirloomrecipes.core.auth

import com.bogadevelopment.heirloomrecipes.features.register.data.ProfileData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.stateIn

class AuthRepositoryImpl(private val auth: FirebaseAuth) : AuthRepository {


    override val currentUser: Flow<ProfileData?> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuthInstance ->
            val firebaseUser: FirebaseUser? = firebaseAuthInstance.currentUser
            println("DEBUG_AUTH: AuthStateListener triggered. FirebaseUser: $firebaseUser")
            if (firebaseUser != null) {
                val profileData = ProfileData(
                    firebaseUid = firebaseUser.uid,
                    email = firebaseUser.email ?: "",
                    name = "",
                    lastName = ""
                )
                println("DEBUG_AUTH: AuthStateListener triggered. FirebaseUser: $firebaseUser")
                trySend(profileData)
            } else {
                println("DEBUG_AUTH: AuthStateListener triggered. FirebaseUser: $firebaseUser")
                trySend(null)
            }
        }

        println("DEBUG_AUTH: Adding AuthStateListener.")
        // USA LA INSTANCIA DEL CONSTRUCTOR 'auth'
        auth.addAuthStateListener(authStateListener)

        awaitClose {    // awaitClose se llama cuando el Flow es cancelado (el colector deja de escuchar)
            println("DEBUG_AUTH: awaitClose - Removing AuthStateListener.")
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(
        scope = CoroutineScope(Dispatchers.IO + SupervisorJob()), // Un scope adecuado para operaciones de IO o Default si no hay IO pesada
        started = SharingStarted.WhileSubscribed(5000L), // Comienza cuando hay suscriptores, se detiene 5s después del último
        initialValue = null // El valor inicial que tendrá el StateFlow
    )

    override suspend fun login(email: String, password: String): Result<ProfileData> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user!!
            Result.success(ProfileData(
                firebaseUid = user.uid,
                name = "", // Podés completar con tu DB
                lastName = "",
                email = user.email ?: ""
            ))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(email: String, password: String, name: String, lastName: String): Result<ProfileData> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user!!
            // Aquí podrías guardar también el profile en tu DB
            Result.success(ProfileData(
                firebaseUid = user.uid,
                name = name,
                lastName = lastName,
                email = email
            ))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        auth.signOut()
    }
}