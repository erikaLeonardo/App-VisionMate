package com.nexianova.visionmateapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import androidx.annotation.NonNull
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.*

class vision_mate : AppCompatActivity() {

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var authStateListener : FirebaseAuth.AuthStateListener

    private lateinit var databaseReference: DatabaseReference
    private lateinit var textViewData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vision_mate)

        val regresar5Btn : ImageView = findViewById(R.id.btnRegresar5)
        val cerrar5Btn : ImageView = findViewById(R.id.btnCerrarSesion5)
        val home5Btn : ImageView = findViewById(R.id.btnHome5)
        val perfil5Btn : ImageView = findViewById(R.id.btnPerfil5)
        val bateriaV : TextView = findViewById(R.id.txtBateria)
        val versionV : TextView = findViewById(R.id.txtVersion)
        val user : TextView = findViewById(R.id.txtUsuario)
        val guardar : Button = findViewById(R.id.btnGuardarCodigo)
        val codigoV : EditText = findViewById(R.id.txtCodi)

        val up : ImageView = findViewById(R.id.btnUpda1)

        firebaseAuth = Firebase.auth

        codigoV.isEnabled = false

        val userText = intent.getStringExtra("USER_TEXT")
        user.text = "\n$userText"
        val correoUsuario = intent.getStringExtra("CORREO_USUARIO")
        user.text = "Usuario: \n$correoUsuario"

        databaseReference = FirebaseDatabase.getInstance().reference.child("/nexianova/datosbaston/-Nkv41yCzeeNGlaqmkpw")

        // Escuchar cambios en la base de datos
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Este método se ejecutará cuando haya cambios en la base de datos
                if (dataSnapshot.exists()) {
                    // Obtener el valor del nodo "mensaje"
                    val bate = dataSnapshot.child("bateria").getValue(String::class.java)
                    val ver = dataSnapshot.child("version").getValue(String::class.java)
                    // Mostrar el valor en el TextView
                    bateriaV.text = "$bate%"
                    versionV.text = "$ver"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar errores de la base de datos aquí
            }
        })


        up.setOnClickListener(){
            codigoV.isEnabled = true
            codigoV.requestFocus()
        }

        guardar.setOnClickListener(){
            val codigoIngresado = codigoV.text.toString()

            if (codigoIngresado != "202023372") {
                // El código ingresado es diferente, mostrar el Toast de "Guardado"
                Toast.makeText(baseContext, "Guardado", Toast.LENGTH_SHORT).show()
            } else {
                // El código ingresado es igual, mostrar el Toast de "Código ya utilizado"
                Toast.makeText(baseContext, "Este codigo ya esta siendo utilizado", Toast.LENGTH_SHORT).show()
            }
        }

        regresar5Btn.setOnClickListener(){
            val i = Intent(this, menuprincipal::class.java)
            val userText = user.text.toString()
            i.putExtra("USER_TEXT", userText)
            i.putExtra("CORREO_USUARIO", correoUsuario)
            startActivity(i)
        }

        home5Btn.setOnClickListener(){
            val i = Intent(this, menuprincipal::class.java)
            val userText = user.text.toString()
            i.putExtra("USER_TEXT", userText)
            i.putExtra("CORREO_USUARIO", correoUsuario)
            startActivity(i)
        }

        perfil5Btn.setOnClickListener(){
            val i = Intent(this, perfil::class.java)
            val userText = user.text.toString()
            i.putExtra("USER_TEXT", userText)
            i.putExtra("CORREO_USUARIO", correoUsuario)
            startActivity(i)
        }

        cerrar5Btn.setOnClickListener(){
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