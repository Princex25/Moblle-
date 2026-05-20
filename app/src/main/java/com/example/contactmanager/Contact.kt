package com.example.contactmanager

import java.io.Serializable

/**
 * Modèle de données pour un Contact.
 * Utilisation d'une data class comme vu en cours (Séance 7).
 * Implémente Serializable pour être passé entre les activités via Intent.
 */
data class Contact(
    val id: Int = 0,
    val name: String,
    val phone: String,
    val email: String? = null
) : Serializable
