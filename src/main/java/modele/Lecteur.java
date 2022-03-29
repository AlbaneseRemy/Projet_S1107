package modele;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;


public class Lecteur implements Serializable {

    // Attributs
    private static final long serialVersionUID = 1L ;  // nécessaire pour la sérialisation
    private final Integer numLecteur ;
    private String nomLecteur ;
    private String prenomLecteur ;
    private LocalDate dateNaissanceLecteur ;
    private String mailLecteur ;
    private HashSet<Emprunt> emprunts ;

    // Constructeur
    public Lecteur (Integer numLecteur, String nomLecteur, String prenomLecteur, LocalDate dateNaissanceLecteur, String mailLecteur) {
        this.numLecteur = numLecteur ;
        this.nomLecteur = nomLecteur ;
        this.prenomLecteur = prenomLecteur ;
        this.dateNaissanceLecteur = dateNaissanceLecteur ;
        this.mailLecteur = mailLecteur ;
        this.emprunts = new HashSet<>();
    }

    // Getters
    public Integer getNumLecteur() {
        return numLecteur ;
    }

    public String getNomLecteur() {
        return nomLecteur ;
    }

    public String getPrenomLecteur() {
        return prenomLecteur ;
    }

    public LocalDate getDateNaissanceLecteur() {
        return dateNaissanceLecteur ;
    }

    public String getMailLecteur() {
        return mailLecteur ;
    }
    
    public Integer getAgeLecteur() {
        int age ;
        LocalDate dateNaissComp;
        LocalDate dateActuelle = LocalDate.now();
        dateNaissComp = LocalDate.of(dateActuelle.getYear(), dateNaissanceLecteur.getMonthValue(), dateNaissanceLecteur.getDayOfMonth());
        if (dateNaissComp.isBefore(dateActuelle)) {
            age = dateActuelle.getYear() - dateNaissanceLecteur.getYear();
        } else {
            age = dateActuelle.getYear() - dateNaissanceLecteur.getYear() - 1;
        }
        return age ;
    }
    
    public HashSet<Emprunt> getEmprunts() {
        return emprunts ;
    }
    
    public Integer getNbEmprunts(){
        return emprunts.size();
    }

    public Boolean estSature (){
        return getNbEmprunts() == 5;
    }

    public void nouvelEmprunt (Exemplaire e){
        Emprunt em = new Emprunt(this, e);
        lierEmprunt(em);
        e.lierEmprunt(em);
    }
    
    private void lierEmprunt (Emprunt em) {
        emprunts.add(em);
    }
    
    public void finEmprunt (Exemplaire e, Emprunt em) {
        this.retirerEmprunt(em) ;
        e.retirerEmprunt() ;
    }
    
    public void retirerEmprunt(Emprunt em) {
        emprunts.remove(em) ;
    }
}
