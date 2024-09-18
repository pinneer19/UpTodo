package dev.uptodo.data.repository

import com.google.firebase.firestore.DocumentReference
import dev.uptodo.data.firebase.model.TaskCategoryDto
import dev.uptodo.data.util.getResult
import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.repository.RemoteTaskCategoryRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreTaskCategoryRepository @Inject constructor(
    private val userDocumentRef: DocumentReference
) : RemoteTaskCategoryRepository {
    override suspend fun getTaskCategories(): Result<Map<String, TaskCategory>> {
        return getResult {
           userDocumentRef
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
            val docRef = userDocumentRef.collection(TASK_CATEGORY_COLLECTION).document()

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
            userDocumentRef.collection(TASK_CATEGORY_COLLECTION)
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
            userDocumentRef.collection(TASK_CATEGORY_COLLECTION)
                .document(id)
                .set(taskCategory)
        }
    }

    companion object {
        private const val TASK_CATEGORY_COLLECTION = "task_category"
    }
}