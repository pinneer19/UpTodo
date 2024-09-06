package dev.uptodo.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import dev.uptodo.data.firebase.model.TaskDto
import dev.uptodo.data.firebase.util.toTaskDto
import dev.uptodo.data.util.getResult
import dev.uptodo.domain.model.Subtask
import dev.uptodo.domain.model.Task
import dev.uptodo.domain.model.TaskPriority
import dev.uptodo.domain.repository.RemoteTaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.tasks.await
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

class FirestoreTaskRepository @Inject constructor(
    private val firebaseDb: FirebaseFirestore
) : RemoteTaskRepository {
    override suspend fun getTasks(): List<Task> {
        return firebaseDb
            .collection(TASK_COLLECTION)
            .dataObjects<Task>()
            .last()
    }

    override suspend fun createTask(
        name: String,
        description: String,
        priority: TaskPriority,
        categoryId: String?,
        subtasks: List<Subtask>,
        deadline: LocalDateTime
    ): Result<String> {
        return getResult {
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

    override suspend fun updateTask(id: String, categoryId: String?, task: Task): Result<Unit> {
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