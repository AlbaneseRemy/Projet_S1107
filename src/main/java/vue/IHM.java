package vue;

import static java.lang.reflect.Array.set;
import modele.*;
import util.* ;

import java.util.Set;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

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
        ES.afficherLibelle("Saisir l'identifiant de l'action choisie : ");
        return Commande.lireCommande();
    }

    private void gererDialogue(Commande cmd) {
        switch (cmd) {
            case QUITTER :
                break ;
            case CREER_LECTEUR :
                bibliotheque.nouveauLecteur(this) ;
                break ;
            case CONSULTER_LECTEUR :
                bibliotheque.consulterLecteur(this) ;
                break ;
            case CREER_OUVRAGE :
                bibliotheque.nouvelOuvrage(this) ;
                break ;
            case CONSULTER_OUVRAGE :
                bibliotheque.consulterOuvrage(this) ;
                break ;
            case CREER_EXEMPLAIRE :
                bibliotheque.nouvelExemplaire(this) ;
                break ;
            case CONSULTER_EXEMPLAIRE :
                bibliotheque.consulterExemplairesOuvrage(this) ;
                break ;
            case EMPRUNTER_EXEMPLAIRE :
                bibliotheque.emprunterExemplaire(this);
                break ;
            case RENDRE_EXEMPLAIRE :
                bibliotheque.rendreExemplaire(this);
                break;
            case RELANCER_LECTEUR :
                bibliotheque.relancerLecteur(this);
                break;
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

    public static class InfosExemplaire {
        public LocalDate dateRecep;
        public Integer nbExemplairesEntres;
        public Integer nbNonEmpruntables;
        
        public InfosExemplaire(LocalDate dateRecep, Integer nbExemplairesEntres, Integer nbNonEmpruntables){
            this.dateRecep=dateRecep;
            this.nbExemplairesEntres=nbExemplairesEntres;
            this.nbNonEmpruntables = nbNonEmpruntables;
        }
    }
    
    public InfosLecteur saisirInfosLecteur (int numLecteur) {
        String nom, prenom, mail ;
        LocalDate dateNaissance ;
        
        ES.afficherTitre("== Saisie d'un lecteur ==");
        nom = ES.lireChaine("Saisir le nom du lecteur :");
        prenom = ES.lireChaine("Saisir le prénom du lecteur :");
        dateNaissance = ES.lireDate("Saisir la date de naissance du lecteur :");
        while(dateNaissance.compareTo(LocalDate.now()) > 0){
            ES.afficherLibelle("La date de naissance doit être antérieure ou égale à la date du jour.");
            dateNaissance = ES.lireDate("Saisir la date de naissance du lecteur :");
        }
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
        while(dateParution.compareTo(LocalDate.now()) > 0){
            ES.afficherLibelle("La date de parution doit être antérieure ou égale à la date du jour.");
            dateParution = ES.lireDate("Saisir la date de parution de l'ouvrage :");
        }
        nomsAuteurs = new ArrayList<>();
        nomsAuteurs.add(ES.lireChaine("Saisir le nom de l'auteur (ou un des noms des auteurs) :"));
        String reponse = ES.lireChaine("Voulez-vous rajouter un auteur ? Saisir o (pour oui) ou n (pour non) :");
        while((!reponse.equals("n")) && (!reponse.equals("o"))){
            reponse = ES.lireChaine("Saisir o (pour oui) ou n (pour non) :");
        }
        while(!reponse.equals("n")){
            nomsAuteurs.add(ES.lireChaine("Saisir le nom de l'auteur (ou un des noms des auteurs) :"));
            reponse = ES.lireChaine("Voulez-vous rajouter un auteur ? Saisir o (pour oui) ou n (pour non) :");
        }
        
        numISBN = ES.lireChaine("Saisir le numéro ISBN :");
        while(listISBN.contains(numISBN)){
            ES.afficherLibelle("Le numéro ISBN saisi existe déjà.");
            numISBN = ES.lireChaine("Saisir le numéro ISBN :");
        }
        
        String pub = ES.lireChaine("Saisir le public visé (enfant, ado, adulte) :");
        while(!pub.equals("enfant") && !pub.equals("ado") && !pub.equals("adulte")){
            pub = ES.lireChaine("Saisir le public visé (enfant, ado, adulte) :");
        }
        switch (pub) {
            case "enfant":
                publicVise = Public.ENFANT;
                break;
            case "ado":
                publicVise = Public.ADO;
                break;
            default:
                publicVise = Public.ADULTE;
                break;
        }
        return new InfosOuvrage(titre, nomEditeur, dateParution, nomsAuteurs, numISBN, publicVise) ;
    }

    public InfosExemplaire saisirInfosExemplaire(LocalDate dateParution){
        Integer nbExemplairesEntres;
        Integer nbNonEmpruntables;
        LocalDate dateRecep;

        ES.afficherTitre("== Saisie d'exemplaires ==");
        dateRecep = ES.lireDate("Saisir la date de réception : ");

        while (dateParution.compareTo(dateRecep) > 0 || dateRecep.compareTo(LocalDate.now()) > 0){
           if(dateParution.compareTo(dateRecep) > 0){
                ES.afficherLibelle("La date de réception doit être postérieure ou égale à la date de parution.");
                ES.afficherLibelle("(date de parution <= date de réception)");
                ES.afficherLibelle("date de parution de l'ouvrage : " + dateParution);
            }
            else if(dateRecep.compareTo(LocalDate.now()) > 0){
                ES.afficherLibelle("La date de réception doit être antérieure ou égale à la date du jour.");
                ES.afficherLibelle("(date de réception <= date du jour)");
            }
            dateRecep = ES.lireDate("Saisir la date de réception : ");
        }
        
        nbExemplairesEntres = ES.lireEntier("Saisir le nombre total d'exemplaires : ");
        nbNonEmpruntables = ES.lireEntier("Saisir le nombre d'exemplaires non empruntables : ");
        while (nbExemplairesEntres < nbNonEmpruntables){
            ES.afficherLibelle("Le nombre d'exemplaires non empruntables doit être inférieur ou égal au nombre d'exemplaires entrés.");
            ES.afficherLibelle("Vous venez d'entrer " + nbExemplairesEntres + " exemplaires.");
            nbNonEmpruntables = ES.lireEntier("Saisir le nombre d'exemplaires non empruntables : ");
        }
        
        return new InfosExemplaire(dateRecep, nbExemplairesEntres, nbNonEmpruntables);
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
            numOuvrage = ES.lireChaine("Cet ISBN ne correspond à aucun ouvrage de la liste. Saisir un nouvel ISBN  : ") ;
        }
        return numOuvrage ;
    }
    
    public Integer saisirNumExemplaire(ArrayList<Integer> listNumExemplaires) {
        Integer numExemplaire = ES.lireEntier("Saisir le numéro de l'exemplaire : ") ;
        while (!listNumExemplaires.contains(numExemplaire)) {
            numExemplaire = ES.lireEntier("Ce numéro d'exemplaire ne correspond à aucun exemplaire de la liste. Saisir un nouveau numéro : ") ;
        }
        return numExemplaire ;
    }

    public void afficherInfosLecteur(final Integer num, final String nom, final String prenom,
                                final LocalDate dateNaissance, final String mail, Integer age) {
        ES.afficherTitre("== affichage du lecteur ==");
        ES.afficherLibelle("numéro : " + num);
        ES.afficherLibelle("nom : " + nom);
        ES.afficherLibelle("prénom : " + prenom);
        ES.afficherLibelle("mail : " + mail);
        ES.afficherLibelle("date de naissance : " + dateNaissance);
        ES.afficherLibelle("âge : " + age + " ans");
    }
    
    public void afficherInfosOuvrage(final String titre, final String nomEditeur, final LocalDate dateParution, final ArrayList<String> nomsAuteurs,
                                final String numISBN, final Public publicVise){
        ES.afficherTitre("== affichage de l'ouvrage ==");
        ES.afficherLibelle("numéro ISBN : " + numISBN);
        ES.afficherLibelle("titre : " + titre);
        ES.afficherLibelle("noms des auteurs : " + nomsAuteurs);
        ES.afficherLibelle("nom de l'éditeur : " + nomEditeur);
        ES.afficherLibelle("date de parution : " + dateParution);
        ES.afficherLibelle("public visé : " + publicVise);
    }
    
    public void afficherInfosOuvrage(final String numOuvrage, final String titre){
        ES.afficherTitre("== affichage des exemplaires de l'ouvrage ==");
        ES.afficherLibelle("Ouvrage : " + numOuvrage);
        ES.afficherLibelle("Titre : " + titre);
        ES.afficherLibelle("n° exemplaire - état") ;
    }

    public void afficherInfosExemplaireOuvrage(Integer numEx, int etat) {          
        String mention = "" ;
        switch (etat) {
            case 1 :
                mention = "\tnon empruntable" ;
                break ;
            case 3 :
                mention = "\tdisponible" ;
                break ;
        }
        ES.afficherLibelle("\t" + numEx + mention);
    }

    public void afficherInfosExemplaireOuvrage(Integer numEx, LocalDate dateEmprunt, LocalDate dateRetour, Integer numLect, String nom, String prenom){  
        //ES.afficherLibelle("numéro d'exemplaire : " + numEx + " -> cet exemplaire est emprunté");
        ES.afficherLibelle("\t" + numEx + "\temprunté");
        ES.afficherLibelle("\t\t   | date d'emprunt : " + dateEmprunt);
        ES.afficherLibelle("\t\t   | date de retour : " + dateRetour);
        ES.afficherLibelle("\t\t   | numéro : " + numLect);
        ES.afficherLibelle("\t\t   | nom : " + nom);
        ES.afficherLibelle("\t\t   | prénom : " + prenom);
    }

    public void afficherInfosLecteurRetard (Integer numLect, String nom, String prenom) {
        ES.afficherLibelle("Lecteur n° " + numLect + " : " + prenom + " " + nom) ;        
    }

    public void afficherInfosRetard (String titre, String numISBN, Integer numEx, LocalDate dateEmprunt, LocalDate dateRetour) {
        ES.afficherLibelle("Exemplaire emprunté :") ;
        ES.afficherLibelle("titre : " + titre) ;
        ES.afficherLibelle("ISBN : " + numISBN) ;
        ES.afficherLibelle("numéro de l'exemplaire : " + numEx) ;
        ES.afficherLibelle("Date d'emprunt : " + dateEmprunt) ;
        ES.afficherLibelle("Date de retour : " + dateRetour) ;
   }
    
    public void afficherInfosEmprunt(String titre, String numISBN, Integer numEx, LocalDate dateEmprunt, LocalDate dateRetour){
        ES.afficherTitre("== affichage des emprunts en cours ==");
        ES.afficherLibelle("titre : "+titre);
        ES.afficherLibelle("ISBN : "+numISBN);
        ES.afficherLibelle("numéro de l'exemplaire : " + numEx);
        ES.afficherLibelle("Date d'emprunt : " + dateEmprunt) ;
        ES.afficherLibelle("Date de retour : " + dateRetour) ;
    }

    //-----  Primitives d'affichage  -----------------------------------------------
    public void informerUtilisateur(final String msg, final boolean succes) {
        ES.afficherLibelle((succes ? "[OK]" : "[KO]") + " " + msg);
    }
    
    public void informerUtilisateur (final String msg) {
        ES.afficherLibelle(msg);
    }
}





