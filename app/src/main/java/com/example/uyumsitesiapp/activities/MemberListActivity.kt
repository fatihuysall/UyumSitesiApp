package com.example.uyumsitesiapp.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.uyumsitesiapp.DB
import com.example.uyumsitesiapp.R
import com.example.uyumsitesiapp.models.Uye

class MemberListActivity : AppCompatActivity() {
    lateinit var shortList: MutableList<String>
    lateinit var textView: TextView
    lateinit var listView: ListView
    lateinit var btnSearch: Button
    lateinit var editText: EditText
    lateinit var imageView: ImageView
    lateinit var imageViewCrossIcon: ImageView
    lateinit var adapter: ArrayAdapter<String>
    private var db: DB = DB(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_list)

        textView = findViewById(R.id.txtMemberType)
        listView = findViewById(R.id.listView)
        btnSearch = findViewById(R.id.btnSearch)
        editText = findViewById(R.id.txtEditTextSearch)
        imageView = findViewById(R.id.imageViewAllow)
        imageViewCrossIcon = findViewById(R.id.imgViewCrossIcon)


//        for (number in 0..100){
//            db.deleteMember(number)
//        }
//
//        db.addMember("Üye" , "Geoff Corselles" , "9979172007" , "3085 Forest Dale Road" , "01AKY11" , "a-09" , "asdasd" , "0" , "0" )
//        db.addMember("Kiracı" , "Mehmet Yılmaz" , "555-123-4567" , "1234 Elm Caddesi, İstanbul" , "34ABC01" , "a-06" , "asdasd" , "1" , "0" )
//        db.addMember("Site Kiracısı" , "Ayşe Kaya" , "555-987-6543" , "5678 Çam Sokak, Ankara" , "06XYZ45" , "a-12" , "" , "1" , "0" )
//        db.addMember("Kiracı" , "Ali Demir" , "555-789-1234" , "2837 fatih sokak, Antalya" ,  "16JKL23" , "a-23" , "" , "0" , "0" )
//        db.addMember("Üye" , "Zeynep Öztürk" , "555-234-5678" , "9876 Gül Mahallesi, İzmir" , "27MNO89" , "b-03" , "" , "1" , "0" )
//        db.addMember("Kiracı" , "Hüseyin Aksoy" , "555-876-5432" , "4321 Menekşe Bulvarı, Bursa" , "55PQR12" , "a-03" , "" , "0" , "0" )
//        db.addMember("Üye" , "Fatma Şahin" , "555-345-6789" , "3456 Yasemin Caddesi, Adana" , "39STU67" , "a-12" , "" , "0" , "0" )
//        db.addMember("Kiracı" , "Mustafa Arslan" , "555-654-3210" , "7890 Lale Sokak, Antalya" , "78VWX34" , "a-16" , "" , "1" , "0" )
//        db.addMember("Site Kiracısı" , "Aysun Güneş" , "555-432-1098" , "2109 Zambak Mahallesi, Mersin" , "12YZAB67" , "a-02" , "" , "0" , "0" )
//        db.addMember("Üye" , "Emre Çelik" , "555-210-9876" , "1098 Papatya Bulvarı, İzmir" , "63CDEFG" , "a-20" , "" , "0" , "0" )
//        db.addMember("Üye" , "Sevgi Korkmaz" , "555-678-5432" , "8765 Gül Sk., İstanbul" , "09HIJKL" , "a-09" , "" , "1" , "0" )
//        db.addMember("Site Kiracısı" , "Aysun Güneş" , "927-617-7307" , "5432 Menekşe Caddesi, Ankara" , "74UYT35" , "a-09" , "" , "0" , "0" )
        val fullList = db.allMembers()
        shortList = getShortList(fullList)


        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, shortList)
        listView.adapter = adapter


        imageViewCrossIcon.setOnClickListener {
        editText.setText("")
        }

        textView.setOnClickListener {

            imageView.setImageResource(R.drawable.upicon)
            val popupMenu = PopupMenu(this, textView)
            popupMenu.menuInflater.inflate(R.menu.popup_menu_, popupMenu.menu)
            popupMenu.setOnDismissListener {
                imageView.setImageResource(R.drawable.downicon)
            }
            popupMenu.setOnMenuItemClickListener { item ->

                imageView.setImageResource(R.drawable.downicon)
                when (item.itemId) {
                    R.id.member -> textView.setText("Üyelik")
                    R.id.name -> textView.setText("İsim")
                    R.id.phone -> textView.setText("Telefon")
                    R.id.adress -> textView.setText("Adres")
                    R.id.platenumber -> textView.setText("Plaka Numarası")
                    R.id.blok -> textView.setText("Blok")

                }
                true
            }

            popupMenu.show()

        }


        btnSearch.setOnClickListener {
            dismissKeyboardShortcutsHelper()
            searchMembers()


        }


        listView.setOnItemClickListener { parent, view, position, id ->
            val newList = db.allMembers()
            val intent = Intent(this, NewMemberActivity::class.java)
            for (item in newList) {
                if (item.name == shortList.get(position)) {
                    intent.putExtra("nid", item.nid)
                    intent.putExtra("type", item.type)
                    intent.putExtra("name", item.name)
                    intent.putExtra("phone", item.phone)
                    intent.putExtra("adress", item.address)
                    intent.putExtra("plateNumber", item.plate)
                    intent.putExtra("block", item.block)
                    intent.putExtra("memberNote", item.note)
                    intent.putExtra("payment", item.payment)
                    intent.putExtra("paymentType", item.paymentType)
                }
            }

            startActivity(intent)
        }

        listView.setOnItemLongClickListener { parent, view, position, id ->
            val newList = db.allMembers()
            var alert = AlertDialog.Builder(this)
            alert.setMessage("Silme işleminden emin misiniz ?")
            alert.setPositiveButton("Evet", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(this, "Silme işlemi gerçekleştirilmiştir", Toast.LENGTH_SHORT).show()
                db.deleteMember(newList.get(position).nid)
                shortList.removeAt(position)
                adapter.notifyDataSetChanged()
            })
            alert.setNegativeButton("Hayır", DialogInterface.OnClickListener { dialog, which ->

            })
            alert.show()


            true
        }
    }

    private fun searchMembers() {
        shortList.clear()
        val arr = db.allMembers()

        when (textView.text) {
            "Üyelik" -> {
                for (item in arr) {
                    if (item.type.contains("${editText.text}")) {
                        Log.d("mal", "üyelik tetiklendi")
                        shortList.add(item.name)

                    }

                }
                adapter.notifyDataSetChanged()
            }

            "İsim" -> {
                for (item in arr) {
                    if (item.name.contains("${editText.text}")) {
                        Log.d("mal", "isim tetiklendi")
                        shortList.add(item.name)


                    }

                }
                adapter.notifyDataSetChanged()
            }

            "Telefon" -> {
                for (item in arr) {
                    if (item.phone.contains("${editText.text}")) {
                        Log.d("mal", "telefon tetiklendi")
                        shortList.add(item.name)
                    }

                }
                adapter.notifyDataSetChanged()
            }

            "Adres" -> {
                for (item in arr) {
                    if (item.address.contains("${editText.text}")) {
                        Log.d("mal", "adres tetiklendi")
                        shortList.add(item.name)
                    }

                }
                adapter.notifyDataSetChanged()
            }

            "Plaka Numarası" -> {
                for (item in arr) {
                    if (item.plate.contains("${editText.text}")) {
                        Log.d("mal", "plaka tetiklendi")
                        shortList.add(item.name)
                    }

                }
                adapter.notifyDataSetChanged()
            }

            "Blok" -> {
                for (item in arr) {
                    if (item.block.contains("${editText.text}")) {
                        Log.d("mal", "blok tetiklendi")
                        shortList.add(item.name)
                    }

                }
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun getShortList(fullList: List<Uye>): MutableList<String> {

        var dummyShortList = mutableListOf<String>()
        for (item in fullList) {
            dummyShortList.add(item.name)
        }
        return dummyShortList
    }


    override fun onPause() {
        Log.d("uysal", "pause çalıştı")
        super.onPause()
    }

    override fun onRestart() {
        Log.d("uysal", "restart çalıştı")

        super.onRestart()
    }

    override fun onResume() {
        Log.d("uysal", "resume çalıştı")
        searchMembers()
        adapter.notifyDataSetChanged()
        super.onResume()

    }


}