package com.example.carmanagment

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
            val intent = Intent(this, MainDesk::class.java)
            startActivity(intent)
            if (isEmailValid() && isPasswordValid()) {
                //tworzenie konta firebase
            }
        })
    }

    private fun isEmailValid(): Boolean{
        var valid = false

        if(editTextTextEmailRegister.text.toString().length > 13 &&
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
        val specialCharacters = "-@%\\[\\}+'!/#$^?:;,\\(\"\\)~`.*=&\\{>\\]<_"
        val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[$specialCharacters])(?=\\S+$).{8,20}$"
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
}