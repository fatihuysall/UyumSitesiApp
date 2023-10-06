package com.example.uyumsitesiapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.uyumsitesiapp.DB
import com.example.uyumsitesiapp.R

class MainActivity : AppCompatActivity() {
    lateinit var btnNew : Button

    lateinit var btnMember : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnNew = findViewById(R.id.btnNew)
        btnMember = findViewById(R.id.btnMembers)

        btnNew.setOnClickListener {
            val intent  = Intent(this , NewMemberActivity::class.java)
            startActivity(intent)
        }
        btnMember.setOnClickListener {
            val intent  = Intent(this , MemberListActivity::class.java)
            startActivity(intent)
        }
    }
}