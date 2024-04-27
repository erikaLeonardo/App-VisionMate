package com.nexianova.visionmateapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class cambiar_password : AppCompatActivity() {

    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cambiar_password)

        val cambiarBtn : Button = findViewById(R.id.btnAceptar)
        val correoPass : EditText = findViewById(R.id.txtCorreo)
        val regresar : ImageView = findViewById(R.id.regresarBTNP)


        regresar.setOnClickListener(){
            val i = Intent(this, iniciar_sesion::class.java)
            startActivity(i)
        }

        cambiarBtn.setOnClickListener(){

            if(correoPass.text.toString().trim().isEmpty()){
                correoPass.setError("Ingrese el correo electronico")
            }else{
                sendPasswordReset(correoPass.text.toString())
            }

        }
        firebaseAuth = Firebase.auth
    }

    private fun sendPasswordReset(email : String){

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(){
                task->
            if(task.isSuccessful){
                Toast.makeText(baseContext, "Revise su correo electronico", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(baseContext, "Error, no se pudo concretar el proceso", Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onBackPressed() {
        return
    }
}