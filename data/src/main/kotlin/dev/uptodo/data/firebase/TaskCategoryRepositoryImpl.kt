package dev.uptodo.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import dev.uptodo.data.model.TaskCategoryDto
import dev.uptodo.data.util.getResult
import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.model.json
import dev.uptodo.domain.repository.TaskCategoryRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreTaskCategoryRepositoryImpl @Inject constructor(
    private val firebaseDb: FirebaseFirestore
) : TaskCategoryRepository {
    override suspend fun getTaskCategories(): Result<Map<String, TaskCategory>> {
        return getResult<Map<String, TaskCategory>> {
            firebaseDb
                .collection(TASK_CATEGORY_COLLECTION)
                .get()
                .await()
                .documents
                .associate { doc ->
                    val taskCategoryDto = requireNotNull(
                        doc.toObject(TaskCategoryDto::class.java)
                    )

                    doc.id to taskCategoryDto.toTaskCategory()
                }
        }
    }

    override suspend fun createTaskCategory(
        categoryName: String,
        iconUri: String,
        iconTint: String
    ): Result<String> {
        return getResult<String> {
            val docRef = firebaseDb.collection(TASK_CATEGORY_COLLECTION).document()

            docRef.set(
                TaskCategoryDto(
                    docRef.id, categoryName, iconUri, iconTint
                )
                    .json
            )
                .await()

            docRef.id
        }
    }

    override suspend fun deleteTaskCategory(id: String): Result<Unit> {
        return getResult {
            firebaseDb.collection(TASK_CATEGORY_COLLECTION)
                .document(id)
                .delete()
                .await()
        }
    }

    override suspend fun updateTaskCategory(
        id: String,
        taskCategory: TaskCategory
    ): Result<Unit> {
        return getResult {
            firebaseDb.collection(TASK_CATEGORY_COLLECTION)
                .document(id)
                .set(taskCategory)
        }
    }

    companion object {
        private const val TASK_CATEGORY_COLLECTION = "task_category"
    }
}