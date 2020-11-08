package com.example.carmanagment

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        politicsTextView.paintFlags = politicsTextView.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        politicsTextView.setOnClickListener(View.OnClickListener {
            //wyswietlenie polityki prywatnosci
        })
    }
}