package com.aurumsystem.mydbsqlite

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton

class UpdateActivity : Activity() {

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        val btnClose: ImageButton = findViewById(R.id.btnClose)
        val btnUpdate: Button = findViewById(R.id.btnUpdate)
        btnClose.setOnClickListener {
            finish()
        }
        btnUpdate.setOnClickListener {
            this.finish()
        }
    }
}