package com.example.carmanagment

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
            isValid(editTextTextEmailAddress.text.toString());
        })
    }

    private fun isValid(text: String): Boolean{
        val valid = false

        if(TextUtils.isEmpty(text)){
            editTextTextEmailAddress.setError("Wpisz email")
        }
        else{
            editTextTextEmailAddress.setError(null)

            if(!text.endsWith("gmail.com") || !text.contains("@")){
                editTextTextEmailAddress.setError("Nieprawidłowa forma emaila - '@gmail.com'")
            }
            else{
                editTextTextEmailAddress.setError(null)
                //firebase wyslanie emaila
            }
        }

        return valid;
    }
}