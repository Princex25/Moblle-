package com.example.contactmanager

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var listView: ListView
    private lateinit var btnAdd: Button
    private lateinit var adapter: ContactAdapter
    private var contactList: MutableList<Contact> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)
        listView = findViewById(R.id.listViewContacts)
        btnAdd = findViewById(R.id.btnAddContact)

        loadContacts()

        // Ajouter un contact
        btnAdd.setOnClickListener {
            val intent = Intent(this, ContactFormActivity::class.java)
            startActivity(intent)
        }

        // Cliquer sur un contact pour le modifier
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val contact = contactList[position]
            val intent = Intent(this, ContactFormActivity::class.java)
            intent.putExtra("CONTACT", contact)
            startActivity(intent)
        }

        // Click long pour supprimer
        listView.onItemLongClickListener = AdapterView.OnItemLongClickListener { _, _, position, _ ->
            val contact = contactList[position]
            showDeleteConfirmation(contact)
            true
        }
    }

    override fun onResume() {
        super.onResume()
        loadContacts()
    }

    private fun loadContacts() {
        contactList = dbHelper.getAllContacts().toMutableList()
        adapter = ContactAdapter(this, contactList)
        listView.adapter = adapter
    }

    private fun showDeleteConfirmation(contact: Contact) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Supprimer le contact")
        builder.setMessage("Voulez-vous vraiment supprimer ${contact.name} ?")
        builder.setPositiveButton("Oui") { _, _ ->
            dbHelper.deleteContact(contact.id)
            loadContacts()
        }
        builder.setNegativeButton("Non", null)
        builder.show()
    }
}
