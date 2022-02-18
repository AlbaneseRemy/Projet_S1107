package vue;

import modele.*;
import util.* ;
import java.util.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;
import java.time.LocalDate;

/**
* La classe IHM est responsable des interactions avec l'utilisateur/trice en
* mode texte. C'est une classe qui n'est associée à aucun état : elle ne contient aucun
* attribut d'instance. Aucune méthode de cette classe n'est pas censée modifier ses paramètres,
* c'est pourquoi les paramètres des méthodes sont tous marqués comme `final`.
*/

public class IHM  {

private final Bibliotheque bibliotheque;

public IHM(Bibliotheque bibliotheque) {
    this.bibliotheque = bibliotheque;
}

//-----  affichage menu et saisie des commandes par l'utilisateur  -------------------------------------------------

/**
* afficherInterface permet l'affichage du menu et le choix d'une commande
*par l'utilisateur (dialogueSaisirCommande) puis l'invocation de la méthode
*de la classe Bibliotheque réalisant l'action  (gererDialogue)
*/

public void afficherInterface() {
    Commande cmd;
    do {
        cmd = this.dialogueSaisirCommande();
        this.gererDialogue(cmd);
    } while (cmd != Commande.QUITTER);
}

private Commande dialogueSaisirCommande() {
    ES.afficherTitre("===== Bibliotheque =====");
    ES.afficherLibelle(Commande.synopsisCommandes());
    ES.afficherLibelle("===============================================");
    ES.afficherLibelle("Saisir l'identifiant de l'action choisie :");
    return Commande.lireCommande();
}

private void gererDialogue(Commande cmd) {
    switch (cmd) {
        case QUITTER:
            break;
        case CREER_LECTEUR:
            bibliotheque.nouveauLecteur(this);
            break;
        case CONSULTER_LECTEURS:
            ES.afficherLibelle("non développé");
            break;
        default:
            assert false : "Commande inconnue.";
    }
}

//-----  Classes conteneurs et éléments de dialogue pour le lecteur -------------------------------------------------

/** Classe conteneur pour les informations saisies pour la création d'un
* lecteur. Tous les attributs sont `public` par commodité d'accès.
* Tous les attributs sont `final` pour ne pas être modifiables.
*/

public static class InfosLecteur {
public final Integer num;
public final String nom;
public final String prenom;
public final String adresse;
public final LocalDate dateNaiss;
public final String email;

public InfosLecteur(final Integer num, final String nom, final String prenom, final String adresse, final LocalDate dateNaiss, final String email) {
    this.num = num;
    this.nom = nom;
    this.prenom = prenom;
    this.adresse = adresse ;
    this.dateNaiss = dateNaiss;
    this.email = email;
}
}

public InfosLecteur saisirLecteur() {
    String nom, prenom, adresse, email;
    LocalDate dateNaiss;
    Integer num;

    ES.afficherTitre("== Saisie d'un lecteur ==");
    num =ES.lireEntier("Saisir le numéro du lecteur :");
    nom = ES.lireChaine("Saisir le nom du lecteur :");
    prenom = ES.lireChaine("Saisir le prénom du lecteur :");
    adresse = ES.lireChaine("Saisir l'adresse du lecteur :");
    dateNaiss = ES.lireDate("Saisir la date de naissance du lecteur :");
    email = ES.lireEmail("Saisir l'email du lecteur :");

    return new InfosLecteur(num, nom, prenom, adresse, dateNaiss, email);
}

public void afficherLecteur(final Integer num, final String nom, final String prenom,
                    final LocalDate dateNaiss, final int age, final String email){
    ES.afficherTitre("== affichage du lecteur== " + num);
    ES.afficherLibelle("nom, prénom et mail du lecteur :" + nom + " " + prenom + " " + email);
    ES.afficherLibelle("date de naissance et age du lecteur :" + dateNaiss + " " + age);
}

//-----  Primitives d'affichage  -----------------------------------------------
public void informerUtilisateur(final String msg, final boolean succes) {
    ES.afficherLibelle((succes ? "[OK]" : "[KO]") + " " + msg);
}

public void informerUtilisateur(final String msg) {
    ES.afficherLibelle(msg);
}

}





