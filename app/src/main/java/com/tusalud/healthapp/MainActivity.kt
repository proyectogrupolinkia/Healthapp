package com.tusalud.healthapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.activity.compose.setContent
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //inicializar firebaseAuth
        auth = FirebaseAuth.getInstance()

        //configurar el launcher para el inicio de sesion con Google
        val googleLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                //iniciar sesion con las credenciales de google
                auth.signInWithCredential(credential)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Login con Google exitoso", Toast.LENGTH_SHORT).show()
                            // Aquí podrías navegar a la pantalla principal
                        } else {
                            Toast.makeText(this, "Error al iniciar con Google", Toast.LENGTH_SHORT).show()
                        }
                    }
            } catch (e: ApiException) {
                Toast.makeText(this, "Error en Google Sign-In: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }

        //configurar la interfaz con jetpack compose
        setContent {
            MaterialTheme {
                LoginScreen(
                    auth = auth,
                    googleSignInLauncher = googleLauncher
                )
            }
        }
    }
}

