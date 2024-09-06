package dev.uptodo.app.util

import androidx.compose.ui.graphics.vector.ImageVector
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport

/** Regex to split icon name. */
private val splitter = Regex("\\.")

/** Prefix for Material icons package. */
private const val iconsPackage = "androidx.compose.material."

/** Decodes a Material Icon using its "compose" name.
 *
 * For example:
 * - Icon `Icons.AutoMirrored.TwoTone.ArrowBack`
 * - Looks for class `androidx.compose.material.icons.filled.ArrowBackKt`
 * - Attempts to find method `getArrowBack(androidx.compose.material.icons.Icons$AutoMirrored$TwoTone)`
 * - Executes such method using static instance `Icons.AutoMirrored.TwoTone`
 *
 * Remember that Material Icons are organized as follows:
 * ```
 * Icons.Default.<iconName>  --> androidx.compose.material.icons.filled
 * Icons.Filled.<iconName>   -->  androidx.compose.material.icons.filled
 * Icons.Rounded.<iconName>  -->  androidx.compose.material.icons.rounded
 * Icons.Outlined.<iconName> -->  androidx.compose.material.icons.outlined
 * Icons.TwoTone.<iconName>  -->  androidx.compose.material.icons.twotone
 * Icons.Sharp.<iconName>    -->  androidx.compose.material.icons.sharp
 * Icons.AutoMirrored.Default.<iconName>  --> androidx.compose.material.icons.automirrored.filled
 * Icons.AutoMirrored.Filled.<iconName>   --> androidx.compose.material.icons.automirrored.filled
 * Icons.AutoMirrored.Rounded.<iconName>  --> androidx.compose.material.icons.automirrored.rounded
 * Icons.AutoMirrored.Outlined.<iconName> --> androidx.compose.material.icons.automirrored.outlined
 * Icons.AutoMirrored.Rounded.<iconName>  --> androidx.compose.material.icons.automirrored.rounded
 * Icons.AutoMirrored.TwoTone.<iconName>  --> androidx.compose.material.icons.automirrored.twotone
 * Icons.AutoMirrored.Sharp.<iconName>    --> androidx.compose.material.icons.automirrored.sharp
 * ```
 *
 * Also, don't forget to include in gradle:
 * ```
 * platform("androidx.compose:compose-bom:2024.02.01").also { compose ->
 *     implementation(compose)
 *     androidTestImplementation(compose)
 *     :
 *     implementation("androidx.compose.material:material-icons-core")
 *     implementation("androidx.compose.material:material-icons-extended")
 *     :
 * }
 * ```
 *
 * @param name Icon name.
 * @param default Default icon if [name] is invalid.
 */
fun decodeMaterialIcon(
    name: String,
    default: ImageVector? = Icons.Default.BugReport
): ImageVector? =
    try {
        val parts = splitter.split(name)
        val className: String
        val typeName: String
        val iconName: String
        when (parts.size) {
            3 -> {
                className = buildString {
                    append(iconsPackage)
                    append(parts[0].lowercase())
                    append('.')
                    val type = parts[1].lowercase()
                    if (type == "default") {
                        append("filled")
                    } else {
                        append(type)
                    }
                    append('.')
                    append(parts[2])
                    append("Kt")
                }
                typeName = buildString {
                    append(parts[0])
                    append('.')
                    val type = parts[1]
                    if (type == "Default") {
                        append("Filled")
                    } else {
                        append(type)
                    }
                }
                iconName = parts[2]
            }

            4 -> {
                className = buildString {
                    append(iconsPackage)
                    append(parts[0].lowercase())
                    append('.')
                    append(parts[1].lowercase())
                    append('.')
                    val type = parts[2].lowercase()
                    if (type == "default") {
                        append("filled")
                    } else {
                        append(type)
                    }
                    append('.')
                    append(parts[3])
                    append("Kt")
                }
                typeName = buildString {
                    append(parts[0])
                    append('.')
                    append(parts[1])
                    append('.')
                    val type = parts[2]
                    if (type == "Default") {
                        append("Filled")
                    } else {
                        append(type)
                    }
                }
                iconName = parts[3]
            }

            else -> throw IllegalArgumentException("Invalid icon-name '$name'")
        }
        val typeClass: Any = when (typeName) {
            "Icons.Filled" -> Icons.Filled
            "Icons.Outlined" -> Icons.Outlined
            "Icons.Rounded" -> Icons.Rounded
            "Icons.TwoTone" -> Icons.TwoTone
            "Icons.Sharp" -> Icons.Sharp
            "Icons.AutoMirrored.Filled" -> Icons.AutoMirrored.Filled
            "Icons.AutoMirrored.Outlined" -> Icons.AutoMirrored.Outlined
            "Icons.AutoMirrored.Rounded" -> Icons.AutoMirrored.Rounded
            "Icons.AutoMirrored.TwoTone" -> Icons.AutoMirrored.TwoTone
            "Icons.AutoMirrored.Sharp" -> Icons.AutoMirrored.Sharp
            else -> throw IllegalArgumentException("Invalid icon-name '$name'")
        }
        Class.forName(className).getDeclaredMethod("get$iconName", typeClass.javaClass).invoke(
            null,
            typeClass
        ) as ImageVector
    } catch (e: Throwable) {
        Log.d("icons", "Icon-error: ${e.message}")
        /* Note: if anything goes wrong:
         * - re-throw exception, or
         * - return a default icon (e.g. BugReport)
         *   Warning: this may introduce unexpected results, or
         * - return null - composables should not render icon, or use a different mechanism.
         */
        default
    }