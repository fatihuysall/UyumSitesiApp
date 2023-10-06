package com.example.uyumsitesiapp.activities

import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.RadioGroup.OnCheckedChangeListener
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.uyumsitesiapp.DB
import com.example.uyumsitesiapp.R
import org.w3c.dom.Text

class NewMemberActivity : AppCompatActivity() {

    lateinit var newTextViewType: TextView
    lateinit var txtNewMemberTitle: TextView
    lateinit var newEditTextName: EditText
    lateinit var newEditTextPhone: EditText
    lateinit var newEditTextAdress: EditText
    lateinit var newEditTextPlateNumber: EditText
    lateinit var newEditTextBlockNumber: EditText
    lateinit var newEditTextMemberNote: EditText
    lateinit var switchNewMember: Switch
    lateinit var checkBox1: CheckBox
    lateinit var checkBox2: CheckBox
    lateinit var btnSave: Button
    lateinit var switchBackground: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_member)
        switchNewMember = findViewById(R.id.switchPaymentMethod)
        newTextViewType = findViewById(R.id.txtNewMemberType)
        newEditTextName = findViewById(R.id.edittxtNewName)
        newEditTextPhone = findViewById(R.id.edittxtNewPhone)
        newEditTextAdress = findViewById(R.id.edittxtNewAdress)
        newEditTextPlateNumber = findViewById(R.id.edittxtNewPlateNumber)
        newEditTextBlockNumber = findViewById(R.id.edittxtBlockNumber)
        txtNewMemberTitle = findViewById(R.id.txtYeniUye)
        newEditTextMemberNote = findViewById(R.id.edittxtMemberNote)
        checkBox1 = findViewById(R.id.checkBox1)
        checkBox2 = findViewById(R.id.checkBox2)
        switchBackground = findViewById(R.id.txtswitchBackground)
        val param = switchBackground.layoutParams
        btnSave = findViewById(R.id.btnSave)

        val db = DB(this)

        newTextViewType.setOnClickListener {
            val popupMenu = PopupMenu(this, newTextViewType)
            popupMenu.menuInflater.inflate(R.menu.member_type, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->

                when (item.itemId) {
                    R.id.uye -> newTextViewType.setText("Üye")
                    R.id.sitekiracisi -> newTextViewType.setText("Site Kiracısı")
                    R.id.kiraci -> newTextViewType.setText("Kiracı")


                }
                true
            }
            popupMenu.show()

        }


        val nid = intent.getIntExtra("nid", 0)
        var type = intent.getStringExtra("type")
        var name = intent.getStringExtra("name")
        var phone = intent.getStringExtra("phone")
        var address = intent.getStringExtra("adress")
        var plateNumber = intent.getStringExtra("plateNumber")
        var block = intent.getStringExtra("block")
        var memberNote = intent.getStringExtra("memberNote")
        var payment = intent.getStringExtra("payment")
        var paymentType = intent.getStringExtra("paymentType")

        if (nid != 0) {
            txtNewMemberTitle.setText("Üye Düzenleme")
        }

        newTextViewType.setText(type)
        newEditTextName.setText(name)
        newEditTextPhone.setText(phone)
        newEditTextAdress.setText(address)
        newEditTextPlateNumber.setText(plateNumber)
        newEditTextBlockNumber.setText(block)
        newEditTextMemberNote.setText(memberNote)
        if (payment == "1") {
            switchNewMember.setChecked(true)
            param.height = 350
            checkBox1.setVisibility(View.VISIBLE)
            checkBox2.setVisibility(View.VISIBLE)
        }
        //iş bankası
        if (paymentType == "1") {
            checkBox1.setChecked(true)
        }
        // ziraat
        else if (paymentType == "2") {
            checkBox2.setChecked(true)
        }




        btnSave.setOnClickListener {


            type = newTextViewType.text.toString()
            name = newEditTextName.text.toString()
            phone = newEditTextPhone.text.toString()
            address = newEditTextAdress.text.toString()
            plateNumber = newEditTextPlateNumber.text.toString()
            block = newEditTextBlockNumber.text.toString()
            memberNote = newEditTextMemberNote.text.toString()

            payment = if (switchNewMember.isChecked) {
                "1"
            } else {
                "0"
            }
            if (checkBox1.isChecked) {
                paymentType = "1"
            } else if (checkBox2.isChecked) {
                paymentType = "2"
            } else {
                paymentType = "0"
            }

    if (nid != 0) {
                db.updateMember(nid, type!!, name!!, phone!!, address!!, plateNumber!!, block!!, memberNote!!, payment!!, paymentType!!)
            } else {
                db.addMember(type!!, name!!, phone!!, address!!, plateNumber!!, block!!, memberNote!!, payment!!, paymentType!!)
            }


            Toast.makeText(this, "Üye kaydedildi/düzenlendi", Toast.LENGTH_SHORT).show()


            if (txtNewMemberTitle.text == "Üye Düzenleme") {
                onBackPressed()
            } else {
                newTextViewType.setText("")
                newEditTextName.setText("")
                newEditTextPhone.setText("")
                newEditTextAdress.setText("")
                newEditTextMemberNote.setText("")
                newEditTextBlockNumber.setText("")
                newEditTextPlateNumber.setText("")
                switchNewMember.setChecked(false)
            }
        }

        switchNewMember.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                checkBox1.setVisibility(View.VISIBLE)
                checkBox2.setVisibility(View.VISIBLE)

                param.height = 350
            } else {
                checkBox2.setChecked(false);
                checkBox1.setChecked(false);
                checkBox1.setVisibility(View.GONE)
                checkBox2.setVisibility(View.GONE)
                param.height = 100
            }
        }

        checkBox1.setOnClickListener {
            checkBox2.setChecked(false);
        }
        checkBox2.setOnClickListener {
            checkBox1.setChecked(false);
        }

    }
}