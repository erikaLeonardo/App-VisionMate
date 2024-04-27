package com.nexianova.visionmateapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class iniciar_sesion : AppCompatActivity() {
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var authStateListener : FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)

        val iniciarSesionbtn : Button = findViewById(R.id.btnIniciarSesion)
        val recuperarbtn : TextView = findViewById(R.id.btn_recuperar_contra)
        val registrarbtn : TextView = findViewById(R.id.btn_registrar)
        val txtCorreo : EditText = findViewById(R.id.txtEmailAddress)
        val txtPass : EditText = findViewById(R.id.txtPassword)

        firebaseAuth = Firebase.auth
        iniciarSesionbtn.setOnClickListener(){

            if(txtCorreo.text.toString().trim().isEmpty()){
                txtCorreo.setError("Ingrese su correo electronico")
            }else if(txtPass.text.toString().trim().isEmpty()){
                txtPass.setError("Ingrese su contraseña")
            }else{
                signIn(txtCorreo.text.toString(), txtPass.text.toString())
            }
        }

        recuperarbtn.setOnClickListener(){
            val i = Intent(this, cambiar_password::class.java)
            startActivity(i)
        }

        registrarbtn.setOnClickListener(){
            val i = Intent(this, crear_cuenta::class.java)
            startActivity(i)
        }
    }

    private fun signIn(email:String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
                task ->

            if(task.isSuccessful){
                val user = firebaseAuth.currentUser
                val verificar = user?.isEmailVerified
                if(verificar==true) {

                    Toast.makeText(baseContext, "Bienvenido de nuevo", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, menuprincipal::class.java)
                    i.putExtra("CORREO_USUARIO", email) // Agregar el correo electrónico como extra
                    startActivity(i)

                }else{
                    Toast.makeText(baseContext, "Verifica tu cuenta, da click en el correo que se te ha enviado", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(baseContext, "Algo salió mal, intentalo nuevamente", Toast.LENGTH_SHORT).show()

            }
        }

    }

}