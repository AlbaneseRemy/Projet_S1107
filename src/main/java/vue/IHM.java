package vue;

import modele.*;
import util.* ;

import java.util.Set;
import java.time.LocalDate;

/**
* La classe IHM est responsable des interactions avec l'utilisateur/trice en
* mode texte. C'est une classe qui n'est associée à aucun état : elle ne contient aucun
* attribut d'instance. Aucune méthode de cette classe n'est pas censée modifier ses paramètres,
* c'est pourquoi les paramètres des méthodes sont tous marqués comme `final`.
*/

public class IHM  {

    private final Bibliotheque bibliotheque ;

    public IHM (Bibliotheque bibliotheque) {
        this.bibliotheque = bibliotheque ;
    }

    //-----  affichage menu et saisie des commandes par l'utilisateur  -------------------------------------------------

    /**
    * afficherInterface permet l'affichage du menu et le choix d'une commande
    *par l'utilisateur (dialogueSaisirCommande) puis l'invocation de la méthode
    *de la classe Bibliotheque réalisant l'action  (gererDialogue)
    */
    public void afficherInterface() {
        Commande cmd ;
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
            case QUITTER :
                break ;
            case CREER_LECTEUR :
                bibliotheque.nouveauLecteur(this) ;
                break ;
            case CONSULTER_LECTEURS :
                //ES.afficherLibelle("non développé") ;
                bibliotheque.consulterLecteur(this) ;
                break ;
            default :
                assert false : "Commande inconnue." ;
        }
    }

    //-----  Classes conteneurs et éléments de dialogue pour le lecteur -------------------------------------------------

    /** Classe conteneur pour les informations saisies pour la création d'un
    * lecteur. Tous les attributs sont `public` par commodité d'accès.
    * Tous les attributs sont `final` pour ne pas être modifiables.
    */
    public static class InfosLecteur {
        public final String nom ;
        public final String prenom ;
        public final LocalDate dateNaissance ;
        public final String mail ;

        public InfosLecteur (final String nom, final String prenom, final LocalDate dateNaissance, final String mail) {
            this.nom = nom ;
            this.prenom = prenom ;
            this.dateNaissance = dateNaissance ;
            this.mail = mail ;
        }
    }

    public InfosLecteur saisirInfosLecteur (int numLecteur) {
        String nom, prenom, mail ;
        LocalDate dateNaissance ;

        ES.afficherTitre("== Saisie d'un lecteur ==");
        nom = ES.lireChaine("Saisir le nom du lecteur :");
        prenom = ES.lireChaine("Saisir le prénom du lecteur :");
        dateNaissance = ES.lireDate("Saisir la date de naissance du lecteur :");
        mail = ES.lireEmail("Saisir l'email du lecteur :");

        return new InfosLecteur(nom, prenom, dateNaissance, mail) ;
    }

    public Integer saisirNumLecteur (Set <Integer> listNumLecteur) {
        return new Integer() ;  // A FAIRE
    }
    
    public Integer saisirNumOuvrage(Set<Integer> listISBN){
        ihm.informerUtilisateur("Saisir un numéro IBSN : ");
        ES.lireEntier();
        while (listISBN.contains() == false){
            ihm.informerUtilisateur("Saisir un numéro ISBN : ");
            ES.lireEntier();
        }
    }
    
    public void afficherLecteur(final Integer num, final String nom, final String prenom,
                                final LocalDate dateNaissance, final String mail) {
        ES.afficherTitre("== affichage du lecteur== " + num);
        ES.afficherLibelle("nom, prénom et mail du lecteur :" + nom + " " + prenom + " " + mail);
        ES.afficherLibelle("date de naissance et age du lecteur :" + dateNaissance + " " + age);
    }

    //-----  Primitives d'affichage  -----------------------------------------------
    public void informerUtilisateur(final String msg, final boolean succes) {
        ES.afficherLibelle((succes ? "[OK]" : "[KO]") + " " + msg);
    }

    public void informerUtilisateur (final String msg) {
        ES.afficherLibelle(msg);
    }

}





