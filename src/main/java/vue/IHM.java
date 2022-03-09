package vue;

import modele.*;
import util.* ;

import java.util.Set;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

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

    private boolean verifDate(LocalDate dateRecep) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
    public static class InfosOuvrage{
        public final String titre;
        public final String nomEditeur;
        public final LocalDate dateParution;
        public final ArrayList<String> nomsAuteurs;
        public final String numISBN;
        public final Public publicVise;
        
        public InfosOuvrage(final String titre, final String nomEditeur, final LocalDate dateParution, final ArrayList<String> nomsAuteurs, final String numISBN, final Public publicVise){
            this.titre = titre;
            this.nomEditeur = nomEditeur;
            this.dateParution = dateParution;
            this.nomsAuteurs = nomsAuteurs;
            this.numISBN = numISBN;
            this.publicVise = publicVise;
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
    
    public InfosOuvrage saisirInfosOuvrage (Set<String> listISBN) {
        String titre, nomEditeur, numISBN ;
        LocalDate dateParution ;
        ArrayList<String> nomsAuteurs;
        Public publicVise;
        
        
        ES.afficherTitre("== Saisie d'un Ouvrage ==");
        titre = ES.lireChaine("Saisir le titre de l'ouvrage :");
        nomEditeur = ES.lireChaine("Saisir le nom de l'éditeur :");
        dateParution = ES.lireDate("Saisir la date de parution de l'ouvrage :");
        // while à faire
        while(){
            nomsAuteurs.add(ES.lireChaine("Saisir les noms des auteurs :"));
        }
        numISBN = ES.lireChaine("Saisir le numéro ISBN :");
        // String...
        publicVise = ES.lireChaine("Saisir le public visé (Enfant, Ado, Adulte) :");

        return new InfosOuvrage(titre, nomEditeur, dateParution, nomsAuteurs, numISBN, publicVise) ;
    }

    public Integer saisirNumLecteur (Set <Integer> listNumLecteur) {
        Integer numLecteur ;
        informerUtilisateur("Saisir un numéro de lecteur : ") ;
        numLecteur = ES.lireEntier() ;
        while (listNumLecteur.contains(numLecteur) == false) {
            numLecteur = ES.lireEntier("Saisir un numéro de lecteur valide : ") ;
        }
        return numLecteur ;
    }

    // Pour consulterOuvrage et consulterExemplairesOuvrage
    public String saisirNumOuvrage (Set <String> listISBN) {
        String numOuvrage = ES.lireChaine("Saisir un numéro ISBN : ") ;
        while(!listISBN.contains(numOuvrage)) {
            numOuvrage = ES.lireChaine("Saisir un numéro ISBN valide : ") ;
        }
        return numOuvrage ;
    }
    
    public void afficherInfosLecteur(final Integer num, final String nom, final String prenom,
                                final LocalDate dateNaissance, final String mail) {
        ES.afficherTitre("==affichage du lecteur== " + num);
        ES.afficherLibelle("nom, prénom et mail du lecteur :" + nom + " " + prenom + " " + mail);
        ES.afficherLibelle("date de naissance et age du lecteur :" + dateNaissance + " " + "age"); // A FAIRE //
    }
    
    public void afficherInfosOuvrage(final String titre, final String nomEditeur, final LocalDate dateParution, final ArrayList<String> nomsAuteurs,
                                final String numISBN, final Public publicVise){
        ES.afficherTitre("==affichage de l'ouvrage== " + numISBN);
        ES.afficherLibelle("titre, nom de l'éditeur, date de parution :" + titre + " " + nomEditeur + " " + dateParution);
        ES.afficherLibelle("noms des auteurs, numéro ISBN, public visé :" + nomsAuteurs + " " + numISBN + " " + publicVise);
    }
    
    public void afficherInfosOuvrage(final String numOuvrage, final String titre){
        ES.afficherTitre("==affichage de l'ouvrage== " + numOuvrage + ", " + titre);
    }
    
    public void afficherInfosExemplaireOuvrage(final Integer numEx){
        ES.afficherLibelle("numéro d'exemplaire :" + numEx);
    }

    //-----  Primitives d'affichage  -----------------------------------------------
    public void informerUtilisateur(final String msg, final boolean succes) {
        ES.afficherLibelle((succes ? "[OK]" : "[KO]") + " " + msg);
    }

    public static class InfosExemplaire {
        private LocalDate dateRecep;
        private Integer nbExemplairesEntres;
        private Integer nbNonEmpruntables;
        
        public InfosExemplaire(LocalDate dateRecep, Integer nbExemplairesEntres, Integer nbNonEmpruntables){
            this.dateRecep=dateRecep;
            this.nbExemplairesEntres=nbExemplairesEntres;
            this.nbNonEmpruntables = nbNonEmpruntables;
        }
    }
    
    public InfosExemplaire saisirInfosExemplaire(LocalDate dateParution){
        Integer nbExemplairesEntres;
        Integer nbNonEmpruntables;
        LocalDate dateRecep;
        
        
        ES.afficherTitre("== Combien d'exemplaires rentrez vous ? ==");
        nbExemplairesEntres = ES.lireEntier("Saisir le nombre total : ");
        nbNonEmpruntables = ES.lireEntier("Saisir le nombre d'exemplaires non empruntables : ");
        dateRecep = ES.lireDate("A quelle date l'avez vous reçu ?");
        
        while (!verifDate(dateRecep)){
            return new InfosExemplaire(dateParution, nbExemplairesEntres, nbNonEmpruntables); 
        }
        else{
        return null;
            }
        }
        
    
    public void informerUtilisateur (final String msg) {
        ES.afficherLibelle(msg);
    }

}





