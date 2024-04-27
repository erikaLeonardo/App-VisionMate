package com.nexianova.visionmateapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class perfil : AppCompatActivity() {

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var authStateListener : FirebaseAuth.AuthStateListener
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val regresar4Btn : ImageView = findViewById(R.id.btnRegresar4)
        val cerrar4Btn : ImageView = findViewById(R.id.btnCerrarSesion4)
        val guardarBtn : Button = findViewById(R.id.btnGuardar)
        val home4Btn : ImageView = findViewById(R.id.btnHome4)
        val perfil4Btn : ImageView = findViewById(R.id.btnPerfil4)
        val user : TextView = findViewById(R.id.txtUsuario)
        val correoTxt : TextView = findViewById(R.id.txtCorreo)
        val nombreTxt : TextView = findViewById(R.id.txtNombre)
        val numTelefonoTxt : TextView = findViewById(R.id.txtTelefono)
        val generoPerfilTxt : TextView = findViewById(R.id.txtGeneroP)

        val up1P : ImageView = findViewById(R.id.btnUpdate1)
        val up2P : ImageView = findViewById(R.id.btnUpdate2)
        val up3P : ImageView = findViewById(R.id.btnUpdate3)
        val up4P : ImageView = findViewById(R.id.btnUpdate4)

        val correoUsuario = intent.getStringExtra("CORREO_USUARIO")
        user.text = "Usuario: \n$correoUsuario"

        firebaseAuth = Firebase.auth

        correoTxt.isEnabled = false
        nombreTxt.isEnabled = false
        numTelefonoTxt.isEnabled = false
        generoPerfilTxt.isEnabled = false

        regresar4Btn.setOnClickListener(){
            val i = Intent(this, menuprincipal::class.java)
            val userText = user.text.toString()
            i.putExtra("USER_TEXT", userText)
            i.putExtra("CORREO_USUARIO", correoUsuario)
            startActivity(i)
        }

        home4Btn.setOnClickListener(){
            val i = Intent(this, menuprincipal::class.java)
            val userText = user.text.toString()
            i.putExtra("USER_TEXT", userText)
            i.putExtra("CORREO_USUARIO", correoUsuario)
            startActivity(i)
        }

        cerrar4Btn.setOnClickListener(){
            singout()
        }

        // Obtener datos automáticamente al iniciar la actividad
        val userText = user.text.toString()
        db.collection("userPerfil").document(userText).get().addOnSuccessListener {
            correoTxt.setText(it.get("correo") as String?)
            nombreTxt.setText(it.get("nombre") as String?)
            numTelefonoTxt.setText(it.get("telefono") as String?)
            generoPerfilTxt.setText(it.get("genero") as String?)
        }

        guardarBtn.setOnClickListener {
            val userText = user.text.toString()

            // Obtener los datos de los EditText
            val correo = correoTxt.text.toString()
            val nombre = nombreTxt.text.toString()
            val telefono = numTelefonoTxt.text.toString()
            val genero = generoPerfilTxt.text.toString()

            // Verificar que los campos no estén vacíos antes de guardar en la base de datos
            if (correo.isNotEmpty() && nombre.isNotEmpty() && telefono.isNotEmpty() && genero.isNotEmpty()) {
                // Guardar en la base de datos
                db.collection("userPerfil").document(userText).set(
                    hashMapOf(
                        "correo" to correo,
                        "nombre" to nombre,
                        "telefono" to telefono,
                        "genero" to genero
                    )
                ).addOnSuccessListener {
                    // Mostrar un Toast indicando que los datos se guardaron correctamente
                    Toast.makeText(baseContext, "Guardado", Toast.LENGTH_SHORT).show()
                    correoTxt.isEnabled = false
                    nombreTxt.isEnabled = false
                    numTelefonoTxt.isEnabled = false
                    generoPerfilTxt.isEnabled = false
                }.addOnFailureListener {
                    // Mostrar un Toast indicando que hubo un error al guardar los datos
                    Toast.makeText(baseContext, "Error al Guardar", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Mostrar un Toast indicando que todos los campos deben ser completados
                Toast.makeText(baseContext, "Ningun campo puede quedar vacio", Toast.LENGTH_SHORT).show()
            }
        }


        up1P.setOnClickListener(){
            // Habilitar el EditText cuando se hace clic en el botón
            correoTxt.isEnabled = true
            // Puedes solicitar el foco para que el usuario pueda comenzar a escribir inmediatamente
            correoTxt.requestFocus()
        }

        up2P.setOnClickListener(){
            nombreTxt.isEnabled = true
            nombreTxt.requestFocus()
        }

        up3P.setOnClickListener(){
            numTelefonoTxt.isEnabled = true
            numTelefonoTxt.requestFocus()
        }

        up4P.setOnClickListener(){
            generoPerfilTxt.isEnabled = true
            generoPerfilTxt.requestFocus()
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