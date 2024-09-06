package dev.uptodo.data.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dev.uptodo.data.local.AppDatabase
import dev.uptodo.data.local.dao.TaskCategoryDao
import dev.uptodo.data.local.dao.TaskDao
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
    @Reusable
    internal fun provideCurrentUserDocumentReference(
        accountService: AccountService
    ): DocumentReference {
        return Firebase.firestore
            .collection(FIREBASE_USERS_COLLECTION)
            .document(accountService.currentUserId)
    }

    @Provides
    @Reusable
    internal fun provideTaskDao(appDatabase: AppDatabase): TaskDao {
        return appDatabase.taskDao
    }

    @Provides
    @Reusable
    internal fun provideTaskCategoryDao(appDatabase: AppDatabase): TaskCategoryDao {
        return appDatabase.taskCategoryDao
    }

    companion object {
        private const val FIREBASE_USERS_COLLECTION = "users"
    }
}