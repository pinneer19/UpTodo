package dev.uptodo.app.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import dev.uptodo.domain.model.Task
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {
    val TaskNavType = object : NavType<Task>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): Task? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Task {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun put(bundle: Bundle, key: String, value: Task) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: Task): String {
            return Uri.encode(Json.encodeToString(value))
        }
    }
}