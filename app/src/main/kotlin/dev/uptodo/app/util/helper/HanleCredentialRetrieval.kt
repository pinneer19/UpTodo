package dev.uptodo.app.util.helper

import android.content.Context
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.GetCredentialInterruptedException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import dev.uptodo.app.R

internal suspend fun handleCredentialRetrieval(
    context: Context,
    authAction: (Credential) -> Unit,
    onShowError: suspend (String) -> Unit
) {
    val credentialManager = CredentialManager.create(context)

    val googleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(context.getString(R.string.default_web_client_id))
        .build()

    val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    try {
        val result = credentialManager.getCredential(
            context = context,
            request = request
        )
        authAction(result.credential)

    } catch (e: GetCredentialException) {
        val errorMessage = when (e) {
            is NoCredentialException ->
                "No credential available. Use regular register instead"

            is GetCredentialCancellationException ->
                "Login was canceled"

            is GetCredentialInterruptedException -> ""
            else -> "An error occurred during authentication"
        }

        onShowError(errorMessage)
    }
}