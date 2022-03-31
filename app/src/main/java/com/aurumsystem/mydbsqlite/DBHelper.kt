package com.aurumsystem.mydbsqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.security.Key
import java.sql.RowId



public class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    factory,
    DATABASE_VERSION
) {
    companion object{
        private const val DATABASE_NAME = "dbData.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "tbUser"
        public const val ID_COL = "id"
        public const val NAME_COL = "name"
        public const val AGE_COL = "age"
        public var IDRow: Long = 0

    }
    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE " +TABLE_NAME+ "(" + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COL + " TEXT," +
                AGE_COL + " TEXT" + ")")
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addData(name: String, age: String){
        val values = ContentValues()
        values.put(NAME_COL, name)
        values.put(AGE_COL, age)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)

    }
    fun addDatas(name: String?, age: String?) : Boolean{
        val values = ContentValues()
        values.put(NAME_COL, name)
        values.put(AGE_COL, age)
        val db = this.writableDatabase

        var cursor:Cursor = db.rawQuery("Select * from " + TABLE_NAME + " where name = '$name'", null)
        if(cursor.moveToFirst()){
            return false
        }
        else{
            IDRow = db.insert(TABLE_NAME, null, values)
            return true

        }

    }

    fun getData(): Cursor?{
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

    }


    @SuppressLint("Range")
    fun getAllData(): Collection<String>{
        val db: SQLiteDatabase = this.readableDatabase
        val arrayList = ArrayList<String>()
        val res: Cursor = db.rawQuery("Select * from " + TABLE_NAME, null)

        res.moveToFirst()

        while (!res.isAfterLast){
            arrayList.add(res.getString(res.getColumnIndex("name")))
            //arrayList.add(res.getString(res.getColumnIndex("id")))
            res.moveToNext()
        }
        return arrayList
    }

    fun deleteData(Name: String){
        val db: SQLiteDatabase = this.writableDatabase
        db.execSQL("Delete from " + TABLE_NAME + " where name = '$Name'")
        //db.delete(TABLE_NAME, RowId + "=" + rowId )
        db.close()
    }

}