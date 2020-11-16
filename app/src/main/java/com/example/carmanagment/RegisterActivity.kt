package com.example.carmanagment

import android.R.attr.password
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        politicsTextView.paintFlags = politicsTextView.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        politicsTextView.setOnClickListener(View.OnClickListener {
            //wyswietlenie polityki prywatnosci


            val mBuilder = AlertDialog.Builder(this)

            val mView: View = layoutInflater.inflate(R.layout.politics_layout, null)
            mBuilder.setView(mView)

            val politicsDialog = mBuilder.create()

            politicsDialog.show()

        })

        registerButton.setOnClickListener(View.OnClickListener {

            registerWithEmailAndPassword()
        })
    }

    private fun registerWithEmailAndPassword(){

        if(isOnline(this)){
            if (isEmailValid() && isPasswordValid()){

                var firebaseAuth : FirebaseAuth
                firebaseAuth = FirebaseAuth.getInstance()

                firebaseAuth.createUserWithEmailAndPassword(editTextTextEmailRegister.text.toString(), editTextTextPasswordRegister.text.toString())
                    .addOnCompleteListener(this,
                        OnCompleteListener<AuthResult?> { task ->
                            if (task.isSuccessful) {
                                val user: FirebaseUser? = firebaseAuth.getCurrentUser()
                                Toast.makeText(
                                    this, "Rejestracja udana.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    this, "Rejestracja nieudana.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
            }
        }
        else{
            Toast.makeText(this, "Brak połączenia z internetem", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isEmailValid(): Boolean{
        var valid = false

        if(editTextTextEmailRegister.text.toString().length > 12 &&
                editTextTextEmailRegister.text.toString().endsWith("@gmail.com")) {
            valid = true
        }
        else{
            editTextTextEmailRegister.setError("Email powinien zawierać 'gmail.com' ")
            valid = false
        }
        return valid;
    }

    private fun isPasswordValid(): Boolean {
        var valid = false
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$"
        pattern = Pattern.compile(PASSWORD_REGEX)

        matcher = pattern.matcher(editTextTextPasswordRegister.text.toString())
        if(matcher.matches()){
            editTextTextPasswordRegister.setError(null)
            valid = true
        }
        else{
            editTextTextPasswordRegister.setError("Hasło powinno zawierać: 0-9, A-Z, !-&")
            valid = false
        }
        return valid
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
}