package com.aurumsystem.mydbsqlite

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.w3c.dom.Text
import java.util.jar.Attributes

class MainActivity : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnAdd: Button = findViewById(R.id.btnAdd)
        val btnPrint: Button = findViewById(R.id.btnPrint)
        val tiName: TextInputLayout = findViewById(R.id.tiName)
        val tiAge: TextInputLayout = findViewById(R.id.tiAge)
        val Name: TextView = findViewById(R.id.Name)
        val Age: TextView = findViewById(R.id.Age)
        val db = DBHelper(this, null)
        val name = tiName.editText?.text.toString()
        val age = tiAge.editText?.text.toString()
        btnAdd.setOnClickListener {

            db.addData(name, age)
            Toast.makeText(this, name + " added to database", Toast.LENGTH_LONG).show()
            tiName.editText?.text?.clear()
            tiAge.editText?.text?.clear()
        }

        btnPrint.setOnClickListener {
            val db = DBHelper(this, null)
            val cursor = db.getData()
            cursor!!.moveToFirst()
            Name.append(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COL)) + "\n")
            Age.append(cursor.getString(cursor.getColumnIndex(DBHelper.AGE_COL)) + "\n")

            while(cursor.moveToNext()){
                Name.append(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COL)) + "\n")
                Age.append(cursor.getString(cursor.getColumnIndex(DBHelper.AGE_COL)) + "\n")
            }
            cursor.close()

        }

    }
}