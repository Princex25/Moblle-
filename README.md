# Application de Gestion de Contacts (Android Kotlin)

Cette application a été développée en suivant strictement les concepts enseignés en cours.

## Fonctionnalités implémentées
- **Affichage des contacts** : Utilisation d'une `ListView` avec un `ContactAdapter` personnalisé.
- **Persistance des données** : Utilisation de **SQLite** via la classe `DatabaseHelper`.
- **Ajout / Modification** : Formulaire avec validation du champ téléphone (obligatoire).
- **Actions rapides** : 
    - Bouton **Appeler** : Utilise `Intent.ACTION_DIAL` (ne nécessite pas de permission CALL_PHONE).
    - Bouton **SMS** : Utilise `Intent.ACTION_SENDTO` avec le schéma `smsto:`.
- **Suppression** : Click long sur un item avec demande de confirmation via `AlertDialog`.

## Structure du Code
- `Contact.kt` : Data class représentant le modèle de données.
- `DatabaseHelper.kt` : Gère la création de la table et les opérations CRUD.
- `MainActivity.kt` : Écran principal affichant la liste.
- `ContactAdapter.kt` : Adaptateur pour lier les données à la ListView.
- `ContactFormActivity.kt` : Écran d'ajout et de modification.

## Conformité Pédagogique
- Utilisation de **Kotlin**.
- Architecture simple : une Activité par écran.
- Pas de bibliothèques tierces (Room, Jetpack Compose, etc.) conformément à la règle "ni plus, ni moins".
- Code commenté pour expliquer les parties non évidentes.
# Moblle-
