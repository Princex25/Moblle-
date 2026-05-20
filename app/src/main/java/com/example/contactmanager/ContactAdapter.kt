package com.example.contactmanager

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.example.contactmanager.R

/**
 * Adaptateur personnalisé pour afficher les contacts dans une ListView.
 * Utilise les concepts de base vus en cours.
 */
class ContactAdapter(context: Context, private val contacts: List<Contact>) :
    ArrayAdapter<Contact>(context, 0, contacts) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false)
        }

        val contact = getItem(position)

        val tvName = itemView!!.findViewById<TextView>(R.id.tvContactName)
        val tvPhone = itemView.findViewById<TextView>(R.id.tvContactPhone)
        val btnCall = itemView.findViewById<ImageButton>(R.id.btnCall)
        val btnSms = itemView.findViewById<ImageButton>(R.id.btnSms)

        tvName.text = contact?.name ?: "Inconnu"
        tvPhone.text = contact?.phone

        // Action Appeler
        btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${contact?.phone}")
            context.startActivity(intent)
        }

        // Action SMS
        btnSms.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("smsto:${contact?.phone}")
            context.startActivity(intent)
        }

        return itemView
    }
}
