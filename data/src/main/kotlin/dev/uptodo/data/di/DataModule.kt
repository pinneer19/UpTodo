package dev.uptodo.data.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dev.uptodo.data.local.AppDatabase
import dev.uptodo.domain.repository.AccountService
import javax.inject.Singleton

@Module(includes = [FirebaseBindModule::class, LocalBindModule::class])
class DataModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()
    }

    @Provides
    internal fun provideCurrentUserDocumentReference(
        accountService: AccountService
    ): DocumentReference {
        return Firebase.firestore
            .collection(FIREBASE_USERS_COLLECTION)
            .document(accountService.currentUserId)
    }

    companion object {
        private const val FIREBASE_USERS_COLLECTION = "users"
    }
}