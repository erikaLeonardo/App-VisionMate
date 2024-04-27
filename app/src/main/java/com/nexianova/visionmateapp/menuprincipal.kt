package com.nexianova.visionmateapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class menuprincipal : AppCompatActivity() {

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var authStateListener : FirebaseAuth.AuthStateListener
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menuprincipal)

        val cerrarBtn : ImageView = findViewById(R.id.btnCerrarSesion)
        val familiarBtn : ImageButton = findViewById(R.id.btnFamiliar)
        val bastonBtn : ImageButton = findViewById(R.id.btnBaston)
        val homeBtn : ImageView = findViewById(R.id.btnHome)
        val perfilBtn : ImageView = findViewById(R.id.btnPerfil)
        val user : TextView = findViewById(R.id.txtUsuario)

        firebaseAuth = Firebase.auth

        // Obtener el correo electrónico del Intent
        val userText = intent.getStringExtra("USER_TEXT")
        user.text = "\n$userText"
        val correoUsuario = intent.getStringExtra("CORREO_USUARIO")
        user.text = "Usuario: \n$correoUsuario"

        familiarBtn.setOnClickListener(){
            val i = Intent(this, informacion_familiar::class.java)
            val userText = user.text.toString()
            val correoUsuario = intent.getStringExtra("CORREO_USUARIO")
            i.putExtra("CORREO_USUARIO", correoUsuario)
            i.putExtra("USER_TEXT", userText)
            startActivity(i)
        }

        bastonBtn.setOnClickListener(){
            val i = Intent(this, vision_mate::class.java)
            val userText = user.text.toString()
            i.putExtra("USER_TEXT", userText)
            i.putExtra("CORREO_USUARIO", correoUsuario)
            startActivity(i)
        }

        homeBtn.setOnClickListener(){
            val i = Intent(this, menuprincipal::class.java)
            val userText = user.text.toString()
            i.putExtra("USER_TEXT", userText)
            i.putExtra("CORREO_USUARIO", correoUsuario)
            startActivity(i)
        }

        perfilBtn.setOnClickListener(){
            val i = Intent(this, perfil::class.java)
            val userText = user.text.toString()
            i.putExtra("USER_TEXT", userText)
            i.putExtra("CORREO_USUARIO", correoUsuario)
            startActivity(i)
        }

        cerrarBtn.setOnClickListener(){
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