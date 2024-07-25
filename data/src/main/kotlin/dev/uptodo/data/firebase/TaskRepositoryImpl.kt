package dev.uptodo.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import dev.uptodo.data.model.TaskDto
import dev.uptodo.data.util.getResult
import dev.uptodo.data.util.toTaskDto
import dev.uptodo.domain.model.Subtask
import dev.uptodo.domain.model.Task
import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.model.TaskPriority
import dev.uptodo.domain.repository.TaskRepository
import kotlinx.coroutines.tasks.await
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

class FirestoreTaskRepositoryImpl @Inject constructor(
    private val firebaseDb: FirebaseFirestore
) : TaskRepository {
    override suspend fun getTasks(): Result<Map<String, Task>> {
        return getResult<Map<String, Task>> {
            firebaseDb
                .collection(TASK_COLLECTION)
                .get()
                .await()
                .documents.associate { doc ->
                    doc.id to requireNotNull(
                        doc.toObject(Task::class.java)
                    )
                }
        }
    }

    override suspend fun createTask(
        name: String,
        description: String,
        priority: TaskPriority,
        categoryId: String,
        subtasks: List<Subtask>,
        deadline: LocalDateTime
    ): Result<String> {
        return getResult<String> {
            val docRef = firebaseDb.collection(TASK_COLLECTION).document()

            getResult {
                docRef.set(
                    TaskDto(
                        docRef.id, name, description, priority, categoryId, subtasks, deadline
                    ).json
                )
                    .await()
            }

            docRef.id
        }
    }

    override suspend fun deleteTask(id: String): Result<Unit> {
        return getResult {
            firebaseDb.collection(TASK_COLLECTION)
                .document(id)
                .delete()
                .await()
        }
    }

    override suspend fun updateTask(id: String, categoryId: String, task: Task): Result<Unit> {
        return getResult {
            firebaseDb.collection(TASK_COLLECTION)
                .document(id)
                .set(task.toTaskDto(id, categoryId))
        }
    }

    companion object {
        private const val TASK_COLLECTION = "task"
    }
}