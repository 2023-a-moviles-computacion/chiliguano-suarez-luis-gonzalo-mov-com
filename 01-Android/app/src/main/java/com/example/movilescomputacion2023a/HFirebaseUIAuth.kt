package com.example.movilescomputacion2023a

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.SupportedProvider
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class HFirebaseUIAuth : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hfirebase_uiauth)

        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        btnLogin.setOnClickListener {
            val providers = arrayListOf(
                //Arreglo de PROVIDERS para logearse
                //EJ: Correo, Facebook, tw, google, etc
                AuthUI.IdpConfig.EmailBuilder().build()
            )
            //Construimos el intent de login
            val logearseIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
            //Respuesta del intent de login
            respuestaLoginAuthUi.launch(logearseIntent)
        }
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        btnLogout.setOnClickListener { seDeslogeo() }
        val usuario = FirebaseAuth.getInstance().currentUser
        if(usuario != null){
            val tvBienvenido = findViewById<TextView>(R.id.tv_bienvenido)
            val btnLogin: Button = findViewById<Button>(R.id.btn_login_firebase)
            val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
            btnLogout.visibility = View.VISIBLE
            btnLogin.visibility = View.INVISIBLE
            tvBienvenido.text = usuario.displayName
        }
    }

    fun seDeslogeo(){
        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        val tvBienvenido = findViewById<TextView>(R.id.tv_bienvenido)
        tvBienvenido.text = FirebaseAuth.getInstance().currentUser?.displayName
        tvBienvenido.text = "Bienvenido"
        btnLogout.visibility = View.INVISIBLE
        btnLogin.visibility = View.VISIBLE
        FirebaseAuth.getInstance().signOut()
    }

    //callback del INTENT de LOGIN
    private val respuestaLoginAuthUi = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res: FirebaseAuthUIAuthenticationResult ->
        if (res.resultCode === RESULT_OK) {
            if (res.idpResponse != null) {
                //Logica de negocio
                seLogeo(res.idpResponse!!)
            }
        }

    }


    fun seLogeo(
        res: IdpResponse
    ){
        val btnLogin: Button = findViewById<Button>(R.id.btn_login_firebase)
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        val tvBienvenido = findViewById<TextView>(R.id.tv_bienvenido)
        tvBienvenido.text = FirebaseAuth.getInstance().currentUser?.displayName
        btnLogout.visibility = View.VISIBLE
        btnLogin.visibility = View.INVISIBLE
        if(res.isNewUser == true){
            registrarUsuarioPorPrimeraVez(res)
        }
    }
    fun registrarUsuarioPorPrimeraVez(usuario: IdpResponse){/*usuario.emai*/}
}