package com.example.assignment.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.assignment.R
import com.example.assignment.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding
    var auth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       enableEdgeToEdge()

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.registerbutton.setOnClickListener {
            var intent = Intent(this@MainActivity, RegistrationActivity::class.java
            )
            startActivity(intent)
        }

        mainBinding.loginbutton.setOnClickListener {
            var intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }


       ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}