package com.nexianova.visionmateapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class crear_cuenta : AppCompatActivity() {
    private lateinit var firebaseAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)

        val registarBtn : Button = findViewById(R.id.btnRegistrar)
        val btnRegresar : ImageView = findViewById(R.id.regresarBTN)
        val nombreTxt : EditText = findViewById(R.id.txtNombre)
        val correoTxt : EditText = findViewById(R.id.txtCorreo)
        val passTxt : EditText = findViewById(R.id.txtPassword)
        val confirmartxt : EditText = findViewById(R.id.txtPassCon)

        registarBtn.setOnClickListener() {
            var psw1 = passTxt.text.toString()
            var psw2 = confirmartxt.text.toString()

            if(nombreTxt.text.toString().trim().isEmpty()){
                nombreTxt.setError("Ingrese su nombre")
            }else if(correoTxt.text.toString().trim().isEmpty()){
                correoTxt.setError("Ingrese su correo electronico")
            }else if(passTxt.text.toString().trim().isEmpty()){
                passTxt.setError("Ingrese su contraseña")
            }else if(confirmartxt.text.toString().trim().isEmpty()){
                confirmartxt.setError("Ingrese nuevamente la contraseña")
            }else{
                if (psw1.equals(psw2)) {
                    createAccount(correoTxt.text.toString(), passTxt.text.toString())

                } else {
                    Toast.makeText(
                        baseContext,
                        "Error: Las contraseñas no coinciden",
                        Toast.LENGTH_SHORT
                    ).show()
                    passTxt.requestFocus()
                }
            }

        }


        btnRegresar.setOnClickListener(){
            val i = Intent(this, iniciar_sesion::class.java)
            startActivity(i)
        }
        firebaseAuth = Firebase.auth

    }

    private fun createAccount(email:String, password:String){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){
                task ->
            if(task.isSuccessful){
                sendEmailVerification()
                Toast.makeText(baseContext, "Verifique su correo para concluir su registro", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(baseContext, "Algo salio mal, ERROR:"
                        + task.exception, Toast.LENGTH_SHORT)
            }
        }
    }

    private fun sendEmailVerification(){

        val user = firebaseAuth.currentUser!!
        user.sendEmailVerification().addOnCompleteListener(this){
                task->
            if(task.isSuccessful){
                Toast.makeText(baseContext, "Verifica tu correo en tu cuenta para continuar", Toast.LENGTH_SHORT).show()
            }else{}
        }
    }

    override fun onBackPressed() {
        return
    }
}