package com.example.contactmanager

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ContactFormActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var etName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button
    private lateinit var tvTitle: TextView
    
    private var currentContact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_form)

        dbHelper = DatabaseHelper(this)
        
        etName = findViewById(R.id.etName)
        etPhone = findViewById(R.id.etPhone)
        etEmail = findViewById(R.id.etEmail)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)
        tvTitle = findViewById(R.id.tvFormTitle)

        // Vérifier si on est en mode modification
        if (intent.hasExtra("CONTACT")) {
            currentContact = intent.getSerializableExtra("CONTACT") as Contact
            tvTitle.text = "Modifier le Contact"
            etName.setText(currentContact?.name)
            etPhone.setText(currentContact?.phone)
            etEmail.setText(currentContact?.email)
        }

        btnSave.setOnClickListener {
            saveContact()
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun saveContact() {
        val name = etName.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val email = etEmail.text.toString().trim()

        // Validation obligatoire du téléphone
        if (phone.isEmpty()) {
            etPhone.error = "Le numéro de téléphone est obligatoire"
            return
        }

        if (currentContact == null) {
            // Ajout
            val newContact = Contact(name = name, phone = phone, email = email)
            val result = dbHelper.addContact(newContact)
            if (result != -1L) {
                Toast.makeText(this, "Contact ajouté", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Modification
            val updatedContact = Contact(id = currentContact!!.id, name = name, phone = phone, email = email)
            val result = dbHelper.updateContact(updatedContact)
            if (result > 0) {
                Toast.makeText(this, "Contact mis à jour", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
