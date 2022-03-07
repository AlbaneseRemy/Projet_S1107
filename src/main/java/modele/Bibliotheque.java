package modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import util.ES;
import vue.*;
import vue.IHM.InfosLecteur;

public class Bibliotheque implements Serializable {

    // Attributs
    private static final long serialVersionUID = 1L ;  // nécessaire pour la sérialisation
    private Integer numDernierLecteur ;
    private Map<Integer, Lecteur> lecteurs ;  // association qualifiée par le numéro d'un lecteur
    private Map<Integer, Ouvrage> ouvrages ;  // association qualifiée par l'ISBN d'un ouvrage

    // Constructeur
    public Bibliotheque() {
        this.numDernierLecteur = 0 ;
        this.lecteurs = new HashMap<>() ;
        this.ouvrages = new HashMap<>() ;
    }

    // Cas d'utilisation 'nouveauLecteur'
    public void nouveauLecteur(IHM ihm) {
        incrementerNumDernierLecteur() ;
        Integer nLecteur = getNumDernierLecteur () ;
        IHM.InfosLecteur infosLecteur = ihm.saisirInfosLecteur(nLecteur) ;
        Lecteur l = new Lecteur (nLecteur, infosLecteur.nom, infosLecteur.prenom,
                infosLecteur.dateNaissance, infosLecteur.mail) ;
        lierLecteur (l, nLecteur) ;
        ihm.informerUtilisateur("création du lecteur de numéro : " + nLecteur, true) ;
    }

    // Cas d'utilisation 'nouvelOuvrage'
    public void nouvelOuvrage(IHM ihm){
        IHM.InfosOuvrage infosOuvrage = ihm.saisirOuvrage();
        Ouvrage o = ouvrage.get(infosOuvrage.numISBN);
        if (o == null){
            // public InfosOuvrage(String titre, String nomEditeur, LocalDate dateParution, ArrayList<String> nomAuteurs, Integer numISBN, Public publicVisé)
            o = new Ouvrage(infosOuvrage.titre, infosOuvrage.nomEditeur, infosOuvrage.dateParution, infosOuvrage.nomAuteurs, infosOuvrage.numISBN, infosOuvrage.publicVisé);
            lierOuvrage(o, infosOuvrage.numISBN);
            ihm.informerUtilisateur("création de l'ouvrage de numéro ISBN : " + infosOuvrage.numISBN, true);
        }
        else{
            ihm.informerUtilisateur("Numéro d'ouvrage déjà inscrit dans la base",false);
        }

    } 
    
    
    
    
public void consulterOuvrage(){
    Set<Integer> listISBN = getListISBN();
    Integer numOuvrage = ihm.saisirNumOuvrage(listISBN);
    ihm.informerUtilisateur("Saisir un numéro IBSN : ");
    ES.lireEntier();
    while (listISBN.contains() == false){
        ihm.informerUtilisateur("Saisir un numéro ISBN : ");
        ES.lireEntier();
    }
    Ouvrage o = unOuvrage(numOuvrage);
    
}

public Map<Integer, Lecteur> getLecteurs() {
    return this.lecteur;
}
public Map<Integer, Ouvrage> getOuvrage(){
    return this.ouvrage;
}
    // Cas d'utilisation 'nouvelExemplaire'


    // Cas d'utilisation 'consulterLecteur'
    public void consulterLecteur (IHM ihm) {
        Set <Integer> listNumLecteur = getListNumLecteur () ;
        Integer nLecteur = ihm.saisirNumLecteur (listNumLecteur) ;
        Lecteur l = unLecteur (nLecteur) ;
        ihm.afficherLecteur(l.getNumLecteur(), l.getNomLecteur(), l.getPrenomLecteur(), l.getDateNaissanceLecteur(), l.getMailLecteur()) ;
    }

    // Cas d'utilisation 'consulterOuvrage


    // Cas d'utilisation 'consulterExemplairesOuvrage'


    //
    public void incrementerNumDernierLecteur () {
        numDernierLecteur++ ;
    }

    public int getNumDernierLecteur () {
        return numDernierLecteur ;
    }

    public Map <Integer, Lecteur> getLecteurs() {
        return this.lecteurs ;
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

    public Map <Integer, Ouvrage> getOuvrage() {
        return this.ouvrages ;
    }

    private void lierOuvrage(Ouvrage o, Integer ISBN) {
        this.ouvrage.put(ISBN, o);
    }

}
