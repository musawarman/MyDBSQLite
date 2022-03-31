package com.aurumsystem.mydbsqlite

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView

class ListActivity : AppCompatActivity() {

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val btnAdd: Button = findViewById(R.id.btnAdd)
        val btnPrint: Button = findViewById(R.id.btnPrint)
        val tiName: TextInputLayout = findViewById(R.id.tiName)
        val tiAge: TextInputLayout = findViewById(R.id.tiAge)
        var name = tiName.editText?.text.toString()
        var age = tiAge.editText?.text.toString()

        val db = DBHelper(this, null)

        val arrayList: ArrayList<String> = db.getAllData() as ArrayList<String>
        val listView: ListView = findViewById(R.id.listView)
        val arrayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(this@ListActivity, android.R.layout.simple_list_item_1, arrayList as List<Any?>)
        listView.adapter = arrayAdapter
        val header:MaterialTextView = layoutInflater.inflate(
            android.R.layout.simple_dropdown_item_1line,
            listView,
            false
        ) as MaterialTextView
        header.text = "List of Name"
        header.setTypeface(header.typeface, Typeface.BOLD)
        header.setBackgroundColor(Color.parseColor("#C19A6B"))
        header.setTextColor(Color.parseColor("#1B1811"))

        listView.addHeaderView(header, null, false)
        fun Display(){
            arrayList.clear()
            arrayList.addAll(db.getAllData())
            arrayAdapter.notifyDataSetChanged()
            listView.invalidateViews()
            listView.refreshDrawableState()
            listView.adapter = arrayAdapter;
            Toast.makeText(this, "Data displayed", Toast.LENGTH_LONG).show()
        }
        btnPrint.setOnClickListener {

            Display()
        }
        btnAdd.setOnClickListener {
            if (tiName.editText?.text?.isNotEmpty() == true && tiAge.editText?.text?.isNotEmpty() == true) {
                if (db.addDatas(tiName.editText?.text.toString(), tiAge.editText?.text.toString())) {

                        Toast.makeText(this, "Inserted ", Toast.LENGTH_SHORT).show()
                        Display()

                } else {
                    Toast.makeText(this, "NOT Inserted", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Cannot be blank", Toast.LENGTH_SHORT).show()
                //tiName.error = "Name cannot be blank"
                //tiAge.error = "Age cannot be blank"
            }

        }
        listView.setOnItemClickListener { adapterView, view, i, l ->
            var params: String = adapterView.getItemAtPosition(i).toString()
            val context = this
            //Toast.makeText(this, "Data : " + params, Toast.LENGTH_SHORT).show()
            MaterialAlertDialogBuilder(context).apply {
                setTitle("Data : $params")
                setIcon(R.drawable.ic_delete)
                setMessage("Are you sure want to delete this data ?")
                setPositiveButton("Delete") {_,_ ->
                    db.deleteData(params)
                    Display()
                }

                setNegativeButton("Update") {_,_ ->
                    //var intent = Intent(this@ListActivity, UpdateActivity::class.java)
                    //startActivity(intent)

                    val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    val view = inflater.inflate(R.layout.activity_update, null)
                    val popupWindow = PopupWindow(
                        view, LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,

                    )
                    var lay:LinearLayout = findViewById(R.id.root_layout)
                    TransitionManager.beginDelayedTransition(lay)
                    popupWindow.showAtLocation(
                        lay,
                        Gravity.BOTTOM,
                        0,
                        0
                    )

                }
                setNeutralButton("Cancel"){_,_ ->

                }
            }.create().show()
        }

    }

}