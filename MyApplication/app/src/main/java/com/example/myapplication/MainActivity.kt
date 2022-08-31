package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button01.setOnClickListener{ //edittext에 있는 값을 가져온 후 테스트 뷰에 뿌린다


              val intent = Intent(this,MainActivity2::class.java)
              startActivity(intent)

          val intent02 = Intent(this,MainActivity3::class.java)
              startActivity(intent02)

            Toast.makeText(applicationContext, "넘어가유~", Toast.LENGTH_SHORT).show()

        }

    }
}