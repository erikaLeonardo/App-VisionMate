package com.nexianova.visionmateapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class informacion_familiar : AppCompatActivity() {

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var authStateListener : FirebaseAuth.AuthStateListener
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacion_familiar)

        val regresar2Btn : ImageView = findViewById(R.id.btnRegresar2)
        val cerrar2Btn : ImageView = findViewById(R.id.btnCerrarSesion2)
        val registarFbtn : Button = findViewById(R.id.btnRegistrarG)
        val home2Btn : ImageView = findViewById(R.id.btnHome2)
        val perfil2Btn : ImageView = findViewById(R.id.btnPerfil2)
        val nombreTxt : EditText = findViewById(R.id.txtNombre)
        val edadTxt : EditText = findViewById(R.id.txtEdad)
        val generTxt : EditText = findViewById(R.id.txtGeneroF)
        val telTxt : EditText = findViewById(R.id.txtTelefonoF)

        val user : TextView = findViewById(R.id.txtUsuario)
        val up1 : ImageView = findViewById(R.id.btnUpdate1)
        val up2 : ImageView = findViewById(R.id.btnUpdate2)
        val up3 : ImageView = findViewById(R.id.btnUpdate3)
        val up4 : ImageView = findViewById(R.id.btnUpdate4)

        val correoUsuario = intent.getStringExtra("CORREO_USUARIO")
        user.text = "Usuario: \n$correoUsuario"

        firebaseAuth = Firebase.auth

        nombreTxt.isEnabled = false
        edadTxt.isEnabled = false
        generTxt.isEnabled = false
        telTxt.isEnabled = false

        regresar2Btn.setOnClickListener(){
            val i = Intent(this, menuprincipal::class.java)
            val userText = user.text.toString()
            i.putExtra("USER_TEXT", userText)
            i.putExtra("CORREO_USUARIO", correoUsuario)
            startActivity(i)
        }


        home2Btn.setOnClickListener(){
            val i = Intent(this, menuprincipal::class.java)
            val userText = user.text.toString()
            i.putExtra("USER_TEXT", userText)
            i.putExtra("CORREO_USUARIO", correoUsuario)
            startActivity(i)
        }

        perfil2Btn.setOnClickListener(){
            val i = Intent(this, perfil::class.java)
            val userText = user.text.toString()
            i.putExtra("USER_TEXT", userText)
            i.putExtra("CORREO_USUARIO", correoUsuario)
            startActivity(i)
        }

        cerrar2Btn.setOnClickListener(){
            singout()
        }

        // Obtener datos automáticamente al iniciar la actividad
        val userText = user.text.toString()
        db.collection("familiar").document(userText).get().addOnSuccessListener {
            nombreTxt.setText(it.get("nombre") as String?)
            edadTxt.setText(it.get("edad") as String?)
            generTxt.setText(it.get("genero") as String?)
            telTxt.setText(it.get("telefono") as String?)
        }

        registarFbtn.setOnClickListener {
            val userText = user.text.toString()

            // Obtener los datos de los EditText
            val nombre = nombreTxt.text.toString()
            val edad = edadTxt.text.toString()
            val genero = generTxt.text.toString()
            val telefono = telTxt.text.toString()

            // Verificar que los campos no estén vacíos antes de guardar en la base de datos
            if (nombre.isNotEmpty() && edad.isNotEmpty() && genero.isNotEmpty() && telefono.isNotEmpty()) {
                // Guardar en la base de datos
                db.collection("familiar").document(userText).set(
                    hashMapOf(
                        "nombre" to nombre,
                        "edad" to edad,
                        "genero" to genero,
                        "telefono" to telefono
                    )
                ).addOnSuccessListener {
                    // Mostrar un Toast indicando que los datos se guardaron correctamente
                    Toast.makeText(baseContext, "Guardado", Toast.LENGTH_SHORT).show()
                    nombreTxt.isEnabled = false
                    edadTxt.isEnabled = false
                    generTxt.isEnabled = false
                    telTxt.isEnabled = false
                }.addOnFailureListener {
                    // Mostrar un Toast indicando que hubo un error al guardar los datos
                    Toast.makeText(baseContext, "Error al Guardar", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Mostrar un Toast indicando que todos los campos deben ser completados
                Toast.makeText(baseContext, "Ningun campo puede quedar vacio", Toast.LENGTH_SHORT).show()
            }
        }


        up1.setOnClickListener(){
            // Habilitar el EditText cuando se hace clic en el botón
            nombreTxt.isEnabled = true
            // Puedes solicitar el foco para que el usuario pueda comenzar a escribir inmediatamente
            nombreTxt.requestFocus()
        }

        up2.setOnClickListener(){
            edadTxt.isEnabled = true
            edadTxt.requestFocus()
        }

        up3.setOnClickListener(){
            generTxt.isEnabled = true
            generTxt.requestFocus()
        }

        up4.setOnClickListener(){
            telTxt.isEnabled = true
            telTxt.requestFocus()
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