package com.example.uyumsitesiapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.uyumsitesiapp.models.Uye

class DB(context: Context) : SQLiteOpenHelper(context, DBname, null, version) {


    companion object {
        private val DBname = "notes"
        private val version = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val notetable = "CREATE TABLE \"note\" (\n" +
                "\t\"Nid\"\tINTEGER,\n" +
                "\t\"Type\"\tTEXT,\n" +
                "\t\"Name\"\tTEXT,\n" +
                "\t\"Phone\"\tTEXT,\n" +
                "\t\"Address\"\tTEXT,\n" +
                "\t\"PlateNumber\"\tTEXT,\n" +
                "\t\"Block\"\tTEXT,\n" +
                "\t\"MemberNote\"\tTEXT,\n" +
                "\t\"Payment\"\tTEXT,\n" +
                "\t\"WhitchBank\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"Nid\" AUTOINCREMENT)\n" +
                ");"

        db?.execSQL(notetable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val noteDropTable = "drop table if EXISTS note"
        db?.execSQL(noteDropTable)
        onCreate(db)
    }


    fun addMember(type: String, name: String, phone: String, address: String, plateNumber: String, block: String, memberNote: String, payment: String,  paymentType: String ): Long {
        val db = this.writableDatabase
        val value = ContentValues()

        value.put("Type", type)
        value.put("Name", name)
        value.put("Phone", phone)
        value.put("Address", address)
        value.put("PlateNumber", plateNumber)
        value.put("Block", block)
        value.put("MemberNote", memberNote)
        value.put("Payment" , payment)
        value.put("WhitchBank" , paymentType)

        val addNoteStatus = db.insert("note", null, value)

        db.close()

        return addNoteStatus
    }

    fun allMembers(): List<Uye> {
        val arr = mutableListOf<Uye>()
        val db = this.readableDatabase
        val cursor = db.query("note", null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            val nid = cursor.getInt(0)
            val type = cursor.getString(1)
            val name = cursor.getString(2)
            val phone = cursor.getString(3)
            val address = cursor.getString(4)
            val plateNumber = cursor.getString(5)
            val block = cursor.getString(6)
            val memberNote = cursor.getString(7)
            val payment = cursor.getString(8)
            val paymentType = cursor.getString(9)


            val uye = Uye(
                nid,
                type,
                name,
                phone,
                address,
                plateNumber,
                block,
                memberNote,
                payment,
                paymentType
            )
            arr.add(uye)
        }
        db.close()
        return arr
    }


    fun deleteMember(nid: Int): Int {
        val db = this.writableDatabase
        val status = db.delete("note", "nid = $nid", null)

        db.close()
        return status
    }


    fun updateMember(nid: Int, type: String, name: String, phone: String, address: String, plateNumber: String, block: String, memberNote: String, payment: String, paymentType: String): Int {
        val db = this.writableDatabase
        val content = ContentValues()

        content.put("Type", type)
        content.put("Name", name)
        content.put("Phone", phone)
        content.put("Address", address)
        content.put("PlateNumber", plateNumber)
        content.put("Block", block)
        content.put("MemberNote", memberNote)
        content.put("Payment", payment)
        content.put("WhitchBank", paymentType)

        val status = db.update("note", content, "nid = $nid", null)



        db.close()
        return status
    }
}