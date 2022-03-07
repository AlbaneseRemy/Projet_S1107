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

private int numDernierLecteur;
private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
private Map<Integer, Lecteur> lecteur;  // association qualifiée par le num
private Map<Integer, Ouvrage> ouvrage;

public Bibliotheque() {
        this.lecteur = new HashMap<>();
}

public void nouveauLecteur(IHM ihm) {
    //numDernierLecteur++;

    IHM.InfosLecteur infosLecteur = ihm.saisirLecteur();
    Lecteur l = lecteur.get(infosLecteur.num);
    if (l == null) {
        l = new Lecteur(infosLecteur.num, infosLecteur.nom, infosLecteur.prenom, infosLecteur.dateNaiss, infosLecteur.email);
        lierLecteur(l, infosLecteur.num);
        ihm.informerUtilisateur("création du lecteur de numéro : " + infosLecteur.num, true);

    } else {
        ihm.informerUtilisateur("numéro de lecteur existant", false);
    }
}

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

public void nouvelExemplaire(IHM ihm){
    
    System.out.println("test");
    
    
}

public Map<Integer, Lecteur> getLecteurs() {
    return this.lecteur;
}
public Map<Integer, Ouvrage> getOuvrage(){
    return this.ouvrage;
}

private void lierLecteur(Lecteur l, Integer num) {
    this.lecteur.put(num, l);
}
private void lierOuvrage(Ouvrage o, Integer ISBN){
    this.ouvrage.put(ISBN, o);
}

}
