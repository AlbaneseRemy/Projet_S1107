package modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.* ;
import util.ES;
import vue.*;
import vue.IHM.InfosLecteur;


public class Bibliotheque implements Serializable {

    // Attributs
    private static final long serialVersionUID = 1L ;  // nécessaire pour la sérialisation
    private Integer numDernierLecteur ;
    private Map<Integer, Lecteur> lecteurs ;  // association qualifiée par le numéro d'un lecteur
    private Map<String, Ouvrage> ouvrages ;  // association qualifiée par l'ISBN d'un ouvrage
    //private Map<String, Exemplaire> exemplaires;

    // Constructeur
    public Bibliotheque() {
        this.numDernierLecteur = 0 ;
        this.lecteurs = new HashMap<>() ;
        this.ouvrages = new HashMap<>() ;
        //this.exemplaires = new HashMap<>();
    }

    // Cas d'utilisation 'nouveauLecteur'
    public void nouveauLecteur (IHM ihm) {
        incrementerNumDernierLecteur() ;
        Integer nLecteur = getNumDernierLecteur() ;
        IHM.InfosLecteur infosLecteur = ihm.saisirInfosLecteur(nLecteur) ;
        Lecteur l = new Lecteur (nLecteur, infosLecteur.nom, infosLecteur.prenom,
                infosLecteur.dateNaissance, infosLecteur.mail) ;
        lierLecteur (l, nLecteur) ;
        ihm.informerUtilisateur("création du lecteur de numéro : " + nLecteur, true) ;
    }

    // Cas d'utilisation 'nouvelOuvrage'
    public void nouvelOuvrage (IHM ihm) {
        Set <String> listISBN = getListISBN () ;
        IHM.InfosOuvrage infosOuvrage = ihm.saisirInfosOuvrage(listISBN) ;
        Ouvrage o = new Ouvrage (infosOuvrage.titre, infosOuvrage.nomEditeur, infosOuvrage.dateParution,
                infosOuvrage.nomsAuteurs, infosOuvrage.numISBN, infosOuvrage.publicVise) ;
        lierOuvrage (o, infosOuvrage.numISBN) ;
        ihm.informerUtilisateur("création de l'ouvrage de numéro ISBN : " + infosOuvrage.numISBN, true) ;
    }     

    // Cas d'utilisation 'nouvelExemplaire'
    public void nouvelExemplaire(IHM ihm) {
        Set <String> listISBN = getListISBN () ;
        if (listISBN.size()>0){
            ES.afficherSetStr(listISBN, "Liste des ouvrages existants :");
            String numOuvrage = ihm.saisirNumOuvrage(listISBN) ;
            Ouvrage o = unOuvrage (numOuvrage) ;
            LocalDate dateParution = o.getDateParution() ;
            ES.afficherLibelle("Titre de l'ouvrage : " + o.getTitre()) ;
            ES.afficherLibelle("Date de parution : " + dateParution) ;
            IHM.InfosExemplaire infosExemplaire = ihm.saisirInfosExemplaire(dateParution) ;
            for (int i=0 ; i<infosExemplaire.nbNonEmpruntables ; i++)
                o.ajouterExemplaire (infosExemplaire.dateRecep, false) ;
            for (int i=0 ; i<infosExemplaire.nbExemplairesEntres-infosExemplaire.nbNonEmpruntables ; i++)
                o.ajouterExemplaire (infosExemplaire.dateRecep, true) ;
            if (infosExemplaire.nbExemplairesEntres > 1)
                ihm.informerUtilisateur("Création des exemplaires", true);
            else
                ihm.informerUtilisateur("Création de l'exemplaire", true);
        }
        else {
            ihm.informerUtilisateur("Aucun ouvrage dans la base.") ;
            ihm.informerUtilisateur("Création d'exemplaires", false);
        }
    }

    // Cas d'utilisation 'Lecteur'
    public void consulterLecteur (IHM ihm) {
        Set <Integer> listNumLecteur = getListNumLecteur() ;
        if (listNumLecteur.size()>0){
            ES.afficherSetInt(listNumLecteur, "Liste des lecteurs existants :");
            Integer nLecteur = ihm.saisirNumLecteur(listNumLecteur) ;
            Lecteur l = unLecteur (nLecteur) ;
            ihm.afficherInfosLecteur(l.getNumLecteur(), l.getNomLecteur(), l.getPrenomLecteur(), l.getDateNaissanceLecteur(), l.getMailLecteur(), l.getAgeLecteur()) ;
            HashSet<Emprunt> collecEmprunts = l.getEmprunts();
            if (collecEmprunts.size()>0){
                for (Emprunt em : collecEmprunts){
                    LocalDate dateEmprunt = em.getDateEmprunt();
                    LocalDate dateRetour = em.getDateRetour();
                    Exemplaire e=em.getExemplaire();
                    Integer numEx = e.getNumExemplaire();
                    Ouvrage o = e.getOuvrage();
                    String titre = o.getTitre();
                    String numISBN = o.getNumISBN();
                    ihm.afficherInfosEmprunt(titre, numISBN, numEx, dateEmprunt, dateRetour);
                }
            }
            else {
                ihm.informerUtilisateur("Le lecteur n'a aucun emprunt en cours.");
            }
            ihm.informerUtilisateur("Consultation d'un lecteur et de ses emprunts",true);
        }
        else{
            ihm.informerUtilisateur("Aucun lecteur dans la base.");
            ihm.informerUtilisateur("Consultation de lecteurs", false);
        }
    }

    // Cas d'utilisation 'consulterOuvrage'
    public void consulterOuvrage(IHM ihm){
        Set<String> listISBN = getListISBN();
        if (listISBN.size()>0){
            ES.afficherSetStr(listISBN, "Liste des ouvrages existants :");
            String numOuvrage = ihm.saisirNumOuvrage(listISBN);
            Ouvrage o = unOuvrage(numOuvrage);
            ihm.afficherInfosOuvrage(o.getTitre(), o.getNomEditeur(), o.getDateParution(), o.getNomsAuteurs(), o.getNumISBN(), o.getPublicVise());
        }
        else{
            ihm.informerUtilisateur("Il n'existe pas encore d'ouvrages. \nRetour au menu.");
            ihm.informerUtilisateur("Consultation d'ouvrage ",false);
        }
    }

    // Cas d'utilisation 'consulterExemplairesOuvrage'
    public void consulterExemplairesOuvrage (IHM ihm) {
        Set <String> listISBN = getListISBN () ;
        if (listISBN.size()>0){
            ES.afficherSetStr(listISBN, "Liste des ouvrages existants :");
            String numOuvrage = ihm.saisirNumOuvrage(listISBN) ;
            Ouvrage o = unOuvrage (numOuvrage) ;
            ihm.afficherInfosOuvrage(o.getNumISBN(), o.getTitre()) ;
            ArrayList <Exemplaire> exemplaires = o.getExemplaires() ;
            if (exemplaires.size()>0){
                for (Exemplaire ex : exemplaires ) {
                    if (ex.estDisponible()) {
                        ihm.afficherInfosExemplaireOuvrage(ex.getNumExemplaire(), 3);
                        //ihm.informerUtilisateur("Cet exemplaire est disponible.");
                    }
                    else if (ex.estEmprunte()) {
                        Emprunt em = ex.getEmprunt();
                        Lecteur l = em.getLecteur();
                        ihm.afficherInfosExemplaireOuvrage(ex.getNumExemplaire(), em.getDateEmprunt(), em.getDateRetour(), l.getNumLecteur(), l.getNomLecteur(), l.getPrenomLecteur());
                    }
                    else {
                        ihm.afficherInfosExemplaireOuvrage(ex.getNumExemplaire(), 1);
                        //ihm.informerUtilisateur("Cet exemplaire n'est pas empruntable.");
                    }
                }
                ihm.informerUtilisateur("Consultation d'exemplaires ",true);
            }
            else {
                ihm.informerUtilisateur("pas d'exemplaires /");
                ihm.informerUtilisateur("Consultation d'exemplaires ",false);
            }
        }
        else{
            ihm.informerUtilisateur("Il n'existe pas encore d'ouvrages, il n'existe donc pas encore d'exemplaires.");
            ihm.informerUtilisateur("Consultation d'exemplaires ",false);
        }
    } 

    // Cas d'utilisation 'emprunterExemplaire'
    public void emprunterExemplaire(IHM ihm) {
        Set <Integer> listNumLecteur = getListNumLecteur() ;
        if (listNumLecteur.size()>0){
            ES.afficherSetInt(listNumLecteur,"Liste des lecteurs existants : ");
            Integer nLecteur = ihm.saisirNumLecteur(listNumLecteur);
            Lecteur l = unLecteur(nLecteur);
            boolean sature = l.estSature();
            if (sature == false){
                Set<String> listISBN = getListISBN();
                if (listISBN.size()>0){
                    ES.afficherSetStr(listISBN, "Liste des ouvrages existants : ");
                    String numOuvrage = ihm.saisirNumOuvrage(listISBN);                
                    Ouvrage o = unOuvrage(numOuvrage);
                    ArrayList <Integer> listNumExemplaire = o.getListNumExemplairesOuvrage() ; //Pourquoi a-t-on un arraylist alors que l'on utilise des sets avant
                    if(listNumExemplaire.size()>0){
                        Integer numExemplaire = ihm.saisirNumExemplaire(listNumExemplaire);
                        Exemplaire e = o.getUnExemplaire(numExemplaire);
                        if(e.estDisponible()){
                            Integer age = l.getAgeLecteur();
                            Public publicVise = o.getPublicVise();
                            if(o.verifAdequationPublic(age, publicVise)){
                                l.nouvelEmprunt(e);
                                ihm.informerUtilisateur("L'exemplaire a bien été emprunté.");
                                ihm.informerUtilisateur("Emprunt de l'exemplaire",true);
                            }                            
                            else{
                                ihm.informerUtilisateur("Le lecteur n'a pas l'âge requis pour cet ouvrage.");
                                ihm.informerUtilisateur("Emprunt de l'exemplaire", false);
                            }
                        }
                        else if (e.estEmprunte()) {
                            ihm.informerUtilisateur("L'exemplaire n'est pas disponible.");
                            ihm.informerUtilisateur("Emprunt de l'exemplaire", false);
                        }
                        else {
                            ihm.informerUtilisateur("L'exemplaire n'est pas empruntable.");
                            ihm.informerUtilisateur("Emprunt de l'exemplaire", false);
                        }
                    }
                    else{
                        ihm.informerUtilisateur("Aucun exemplaire n'existe pour cet ouvrage.");
                        ihm.informerUtilisateur("Emprunt de l'exemplaire", false);
                    }
                }                
                else {
                    ihm.informerUtilisateur("Aucun ouvrage dans la base.");
                    ihm.informerUtilisateur("Emprunt de l'exemplaire", false);
                }
            }
            else {
                ihm.informerUtilisateur("Ce lecteur a déjà 5 emprunts en cours.");
                ihm.informerUtilisateur("Emprunt de l'exemplaire", false);
            }
        }
        else{
            ihm.informerUtilisateur("Aucun lecteur dans la base.");
            ihm.informerUtilisateur("Emprunt de l'exemplaire", false);
        }          
    }
    
    // Cas d'utilisation 'rendreExemplaire'
    public void rendreExemplaire (IHM ihm) {
        Set <String> listISBN = getListISBN() ;
        if (listISBN.size() > 0) {
            ES.afficherSetStr(listISBN, "Liste des ouvrages existants :") ;
            String numOuvrage = ihm.saisirNumOuvrage(listISBN) ;
            Ouvrage o = unOuvrage(numOuvrage) ;
            ArrayList <Integer> listNumExemplaires = o.getListNumExemplairesOuvrage() ;
            if (listNumExemplaires.size() > 0) {
                ES.afficherLibelle("Liste des exemplaires existants : " + listNumExemplaires) ;
                Integer numExemplaire = ihm.saisirNumExemplaire(listNumExemplaires) ;
                Exemplaire ex = o.getUnExemplaire(numExemplaire) ;
                if (ex.estEmprunte()) {
                    Emprunt em = ex.getEmprunt() ;
                    Lecteur l = em.getLecteur() ;
                    l.finEmprunt(ex, em) ;
                    ihm.informerUtilisateur("L'exemplaire a bien été rendu.");
                    ihm.informerUtilisateur("Retour de l'exemplaire", true) ;
                }
                else {
                    ihm.informerUtilisateur("Cet exemplaire n'est pas emprunté, il ne peut être rendu.\nRetour au menu");
                }
            }
            else {
                ihm.informerUtilisateur("Cet ouvrage n'a pas d'exemplaires.\nRetour au menu.") ;
            }
        }
        else {
            ihm.informerUtilisateur("Il n'existe pas encore d'ouvrages.\nRetour au menu.") ;
        }
    }

    // Cas d'utilisation 'relancerLecteur'
    public void relancerLecteur (IHM ihm) {
        Collection<Lecteur> collecLecteurs = getLecteurs() ;
        if (collecLecteurs.size() > 0) {
            int nbRetardsTotal = 0 ;
            for (Lecteur l : collecLecteurs) {
                HashSet<Emprunt> emprunts = l.getEmprunts() ;
                if (emprunts.size() > 0) {
                    int nbEmpruntsRetard = 0 ;
                    for (Emprunt em : emprunts) {
                        LocalDate dateRetour = em.getDateRetour() ;
                        if ((dateRetour.plusDays(14)).isBefore(LocalDate.now())) {
                            nbEmpruntsRetard++ ;
                            nbRetardsTotal++ ;
                            LocalDate dateEmprunt = em.getDateEmprunt() ;
                            Exemplaire ex = em.getExemplaire() ;
                            Integer numEx = ex.getNumExemplaire() ;
                            Ouvrage o = ex.getOuvrage() ;
                            String titre = o.getTitre() ;
                            String  numISBN = o.getNumISBN() ;
                            if (nbEmpruntsRetard == 1) {
                                String nom = l.getNomLecteur() ;
                                String prenom = l.getPrenomLecteur() ;
                                Integer numLect = l.getNumLecteur() ;
                                ihm.afficherInfosLecteurRetard (numLect, nom, prenom) ;
                            }
                            ihm.afficherInfosRetard (titre, numISBN, numEx, dateEmprunt, dateRetour) ;
                        } // else : on n'affiche rien le lecteur n'a pas d'emprunts en retards                
                    }
                } // else : on n'affiche rien non plus si le lecteur n'a pas d'emprunts
            }
            if (nbRetardsTotal == 0) {
                ihm.informerUtilisateur("Aucun emprunt en retard actuellement.") ;
            }
            ihm.informerUtilisateur("Consultation des retards",true);
        }
        else {
                ihm.informerUtilisateur("Aucun lecteur dans la base.\nRetour au menu");
            }
    }

    // Méthodes liées aux lecteurs
    public void incrementerNumDernierLecteur () {
        numDernierLecteur++ ;
    }

    public int getNumDernierLecteur () {
        return numDernierLecteur ;
    }

    private Lecteur unLecteur (Integer nLecteur) { 
        return lecteurs.get(nLecteur) ;
    }

    private Set <Integer> getListNumLecteur () {
        return lecteurs.keySet() ;
    }

    private Collection<Lecteur> getLecteurs () {
        return lecteurs.values() ;
    }

    private void lierLecteur (Lecteur l, Integer num) {
        this.lecteurs.put(num, l) ;
    }
    
    // Méthodes liées aux ouvrages
    public Set <String> getListISBN(){
        return ouvrages.keySet();
    }

    private Ouvrage unOuvrage(String numOuvrage) {
        return ouvrages.get(numOuvrage);
    }

    private void lierOuvrage(Ouvrage o, String ISBN) {
        this.ouvrages.put(ISBN, o);
    }
}
