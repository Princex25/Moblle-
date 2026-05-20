package com.example.contactmanager

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Gestionnaire de la base de données SQLite.
 * Suit strictement les instructions : une seule table 'contacts'.
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "contacts_db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_CONTACTS = "contacts"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_PHONE = "phone"
        private const val KEY_EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_PHONE + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    // Ajouter un contact
    fun addContact(contact: Contact): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, contact.name)
        values.put(KEY_PHONE, contact.phone)
        values.put(KEY_EMAIL, contact.email)
        
        val success = db.insert(TABLE_CONTACTS, null, values)
        db.close()
        return success
    }

    // Récupérer tous les contacts
    fun getAllContacts(): List<Contact> {
        val contactList = ArrayList<Contact>()
        val selectQuery = "SELECT * FROM $TABLE_CONTACTS ORDER BY $KEY_NAME ASC"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val contact = Contact(
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL))
                )
                contactList.add(contact)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return contactList
    }

    // Mettre à jour un contact
    fun updateContact(contact: Contact): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, contact.name)
        values.put(KEY_PHONE, contact.phone)
        values.put(KEY_EMAIL, contact.email)

        val success = db.update(TABLE_CONTACTS, values, "$KEY_ID=?", arrayOf(contact.id.toString()))
        db.close()
        return success
    }

    // Supprimer un contact
    fun deleteContact(contactId: Int): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_CONTACTS, "$KEY_ID=?", arrayOf(contactId.toString()))
        db.close()
        return success
    }

    // Récupérer un contact par ID
    fun getContactById(id: Int): Contact? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_CONTACTS, arrayOf(KEY_ID, KEY_NAME, KEY_PHONE, KEY_EMAIL),
            "$KEY_ID=?", arrayOf(id.toString()), null, null, null, null
        )

        var contact: Contact? = null
        if (cursor != null && cursor.moveToFirst()) {
            contact = Contact(
                cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONE)),
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL))
            )
            cursor.close()
        }
        db.close()
        return contact
    }
}
