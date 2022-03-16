package modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

import util.ES;
import vue.*;
import vue.IHM.InfosLecteur;


public class Bibliotheque implements Serializable {

    // Attributs
    private static final long serialVersionUID = 1L ;  // nécessaire pour la sérialisation
    private Integer numDernierLecteur ;
    private Map<Integer, Lecteur> lecteurs ;  // association qualifiée par le numéro d'un lecteur
    private Map<String, Ouvrage> ouvrages ;  // association qualifiée par l'ISBN d'un ouvrage

    // Constructeur
    public Bibliotheque() {
        this.numDernierLecteur = 0 ;
        this.lecteurs = new HashMap<>() ;
        this.ouvrages = new HashMap<>() ;
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
        String numOuvrage = ihm.saisirNumOuvrage(listISBN) ;
        Ouvrage o = unOuvrage (numOuvrage) ;
        LocalDate dateParution = o.getDateParution() ;
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

    // Cas d'utilisation 'consulterLecteur'
    public void consulterLecteur (IHM ihm) {
        Set <Integer> listNumLecteur = getListNumLecteur() ;
        if (listNumLecteur.size()>0){
            ES.afficherSetInt(listNumLecteur, "Liste des lecteurs existants :");
            Integer nLecteur = ihm.saisirNumLecteur(listNumLecteur) ;
            Lecteur l = unLecteur (nLecteur) ;
            ihm.afficherInfosLecteur(l.getNumLecteur(), l.getNomLecteur(), l.getPrenomLecteur(), l.getDateNaissanceLecteur(), l.getMailLecteur(), l.getAgeLecteur()) ;
        }
        else{
            ES.afficherLibelle("Il n'existe pas encore de lecteurs. \nRetour au menu.");
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
            ES.afficherLibelle("Il n'existe pas encore d'ouvrages. \nRetour au menu.");
        }
    }
    

    // Cas d'utilisation 'consulterExemplairesOuvrage'
    public void consulterExemplairesOuvrage (IHM ihm) {
        Set <String> listISBN = getListISBN () ;
        if (listISBN.size()>0){
            ES.afficherSetStr(listISBN, "Liste des ouvrages existants :");
            String numOuvrage = ihm.saisirNumOuvrage(listISBN) ;
            Ouvrage o = unOuvrage (numOuvrage) ;
            if (o.getExemplaires().size()>0){
                ihm.afficherInfosOuvrage(o.getNumISBN(), o.getTitre()) ;
                HashSet <Exemplaire> exemplaires = o.getExemplaires() ;
                ihm.afficherInfosExemplaireOuvrage(exemplaires);
            }
            else {
                ES.afficherLibelle("Il n'existe pas encore d'exemplaires pour cet ouvrage. \nRetour au menu.");
            }
        }
        else{
            ES.afficherLibelle("Il n'y a pas encore d'ouvrages, et donc pas d'exemplaires non plus. \nRetour au menu.");
        }
    } 

    //
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

    private void lierLecteur (Lecteur l, Integer num) {
        this.lecteurs.put(num, l) ;
    }
    
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
