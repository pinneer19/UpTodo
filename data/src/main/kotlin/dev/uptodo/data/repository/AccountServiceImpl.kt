package dev.uptodo.data.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import dev.uptodo.domain.model.User
import dev.uptodo.domain.repository.AccountService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor() : AccountService {
    override val currentUser: Flow<User?>
        get() = callbackFlow {
            val authListener = FirebaseAuth.AuthStateListener { auth ->
                trySend(auth.currentUser?.let { User(it.uid) })
            }

            Firebase.auth.addAuthStateListener(authListener)
            awaitClose { Firebase.auth.removeAuthStateListener(authListener) }
        }

    override val currentUserId: String
        get() = requireNotNull(Firebase.auth.currentUser).uid

    override suspend fun resetPassword(email: String) {
        Firebase.auth.sendPasswordResetEmail(email).await()
    }

    override fun hasUser(): Boolean = Firebase.auth.currentUser != null

    override suspend fun loginWithEmail(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun loginWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.signInWithCredential(credential).await()
    }

    override suspend fun registerWithEmail(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun registerWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.currentUser!!.linkWithCredential(credential).await()
    }

    override suspend fun logout() {
        Firebase.auth.signOut()
    }

    override suspend fun deleteAccount() {
        requireNotNull(Firebase.auth.currentUser).delete().await()
    }
}