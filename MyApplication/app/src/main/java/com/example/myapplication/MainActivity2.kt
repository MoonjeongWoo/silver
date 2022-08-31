package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        button02.setOnClickListener {
            val intent02 = Intent(this, MainActivity::class.java)
            startActivity(intent02);
            Toast.makeText(applicationContext, "해윙~!", Toast.LENGTH_SHORT).show()
            finish();
        }


    }
}