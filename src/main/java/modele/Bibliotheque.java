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
    
public void nouvelExemplaire(IHM ihm){
    Set<String> gi;
    gi = getListISBN();
    String numOuvrage = ihm.saisirNumOuvrage(gi);
    
    
    
    IHM.InfosExemplaire infosExemplaire = ihm.saisirExemplaire();
        
}


    // Cas d'utilisation 'consulterLecteur'
    public void consulterLecteur (IHM ihm) {
        Set <Integer> listNumLecteur = getListNumLecteur() ;
        Integer nLecteur = ihm.saisirNumLecteur(listNumLecteur) ;
        Lecteur l = unLecteur (nLecteur) ;
        ihm.afficherLecteur(l.getNumLecteur(), l.getNomLecteur(), l.getPrenomLecteur(), l.getDateNaissanceLecteur(), l.getMailLecteur()) ;
    }

    // Cas d'utilisation 'consulterOuvrage'
    public void consulterOuvrage(IHM ihm){
        Set<String> listISBN = getListISBN();
        String numOuvrage = ihm.saisirNumOuvrage(listISBN);
        Ouvrage o = unOuvrage(numOuvrage);
        ihm.afficherOuvrage(o.getTitre(), o.getNumISBN(), o.getNomsAuteurs(), o.getNomEditeur(), o.getDateParution(), o.getPublicVise());
    }

    // Cas d'utilisation 'consulterExemplairesOuvrage'
    public void consulterExemplairesOuvrages (IHM ihm) {
        Set <String> listISBN = getListISBN () ;        
        String numOuvrage = ihm.saisirNumOuvrage(listISBN) ;
        Ouvrage o = unOuvrage (numOuvrage) ;
        ihm.afficherInfosOuvrage(numOuvrage, o.getTitre()) ;  // Methode à écrire
        HashSet <Exemplaire> exemplaires = o.getExemplaires() ;
    } 

    //
    public void incrementerNumDernierLecteur () {
        numDernierLecteur++ ;
    }

    public int getNumDernierLecteur () {
        return numDernierLecteur ;
    }
    
    
    /*public Map<Integer, Lecteur> getLecteurs(){
        return this.lecteur;
    }
    public Map<String, Ouvrage> getOuvrages(){
        return this.ouvrage;
    }*/

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
