package com.example.carmanagment

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        forgetPasswordTextView.paintFlags = forgetPasswordTextView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        createAccountTextView.paintFlags = createAccountTextView.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        createAccountTextView.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        })

        forgetPasswordTextView.setOnClickListener(View.OnClickListener {
            isEmailValid(editTextLoginEmail.text.toString());
        })

        loginButton.setOnClickListener(View.OnClickListener {
            if(isOnline(this)) {

                if(isEmailValid(editTextLoginEmail.text.toString())){
                    loginWithEmailAndPassword()
                }
            }
            else{
                Toast.makeText(
                    this, "Brak połączenia z internetem.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun loginWithEmailAndPassword(){

        var firebaseAuth : FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(editTextLoginEmail.text.toString(), editTextLoginPassword.text.toString())
            .addOnCompleteListener(this,
            OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = firebaseAuth.getCurrentUser()
                    Toast.makeText(
                        this, "Logowanie udane.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI()
                } else {
                    Toast.makeText(
                        this, "Logowanie nieudane.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }

    private fun isEmailValid(text: String): Boolean{
        var valid = false

        if(TextUtils.isEmpty(text)){
            editTextLoginEmail.setError("Wpisz email")
        }
        else{
            editTextLoginEmail.setError(null)

            if(!text.endsWith("gmail.com") || !text.contains("@")){
                editTextLoginEmail.setError("Nieprawidłowa forma emaila - '@gmail.com'")
            }
            else{
                editTextLoginEmail.setError(null)
                valid = true
                //firebase wyslanie emaila
            }
        }

        return valid;
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        }
        return false
    }

    private fun updateUI(){

        val intent = Intent(this, MainDesk::class.java)
        startActivity(intent)
    }
}