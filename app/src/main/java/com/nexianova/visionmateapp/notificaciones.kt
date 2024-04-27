package com.nexianova.visionmateapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class notificaciones : AppCompatActivity() {

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var authStateListener : FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notificaciones)

        val regresar3Btn : ImageView = findViewById(R.id.btnRegresar3)
        val cerrar3Btn : ImageView = findViewById(R.id.btnCerrarSesion3)
        val notifi3Btn : ImageView = findViewById(R.id.btnNotificaciones3)
        val home3Btn : ImageView = findViewById(R.id.btnHome3)
        val perfil3Btn : ImageView = findViewById(R.id.btnPerfil3)
        val user : TextView = findViewById(R.id.txtUsuario)

        val correoUsuario = intent.getStringExtra("CORREO_USUARIO")
        user.text = "Usuario: \n$correoUsuario"

        firebaseAuth = Firebase.auth

        regresar3Btn.setOnClickListener(){
            val i = Intent(this, menuprincipal::class.java)
            val userText = user.text.toString()
            i.putExtra("USER_TEXT", userText)
            i.putExtra("CORREO_USUARIO", correoUsuario)
            startActivity(i)
        }

        notifi3Btn.setOnClickListener(){
            val i = Intent(this, notificaciones::class.java)
            val userText = user.text.toString()
            i.putExtra("USER_TEXT", userText)
            i.putExtra("CORREO_USUARIO", correoUsuario)
            startActivity(i)
        }

        home3Btn.setOnClickListener(){
            val i = Intent(this, menuprincipal::class.java)
            val userText = user.text.toString()
            i.putExtra("USER_TEXT", userText)
            i.putExtra("CORREO_USUARIO", correoUsuario)
            startActivity(i)
        }

        perfil3Btn.setOnClickListener(){
            val i = Intent(this, perfil::class.java)
            val userText = user.text.toString()
            i.putExtra("USER_TEXT", userText)
            i.putExtra("CORREO_USUARIO", correoUsuario)
            startActivity(i)
        }

        cerrar3Btn.setOnClickListener(){
            singout()
        }
    }

    override fun onBackPressed() {
        return
    }

    private fun singout(){
        firebaseAuth.signOut()
        val i = Intent(this, iniciar_sesion::class.java)
        startActivity(i)
    }
}