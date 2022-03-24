/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author albanesr
 */
public class Ouvrage implements Serializable {
    
    // Attributs
    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
    private String titre;
    private String nomEditeur;
    private LocalDate dateParution;
    private ArrayList<String> nomAuteurs;
    private final String numISBN;
    private Public publicVise;
    private Integer numDernierExemplaire;
    private ArrayList <Exemplaire> exemplaires;

    // Constructeur
    public Ouvrage(String titre, String nomEditeur, LocalDate dateParution, ArrayList<String> nomAuteurs, String numISBN, Public publicVise) {
        this.titre = titre;
        this.nomEditeur = nomEditeur;
        this.dateParution = dateParution;
        this.nomAuteurs = nomAuteurs;
        this.numISBN = numISBN;
        this.publicVise = publicVise;
        this.numDernierExemplaire = 0;
        this.exemplaires = new ArrayList<>();
    }

    // Methode ajouterExemplaire
    public void ajouterExemplaire (LocalDate dateRecep, Boolean estEmpruntable) {
        incrementerNumDernierExemplaire() ;
        Exemplaire e = new Exemplaire (dateRecep, estEmpruntable, this) ;
        lierExemplaire(e) ;
    }

    public void incrementerNumDernierExemplaire() {
        this.numDernierExemplaire++;
    }

    public void lierExemplaire (Exemplaire e) {
        this.exemplaires.add(e) ;
    }

    // Getters
    public String getTitre(){
        return titre;
    }
    
    public String getNomEditeur(){
        return nomEditeur;
    }
    
    public LocalDate getDateParution(){
        return dateParution;
    }

    public ArrayList<String> getNomsAuteurs() {
        return nomAuteurs;
    }

    public String getNumISBN() {
        return numISBN;
    }

    public Public getPublicVise() {
        return publicVise;
    }

    public Integer getNumDernierExemplaire() {
        return numDernierExemplaire;
    }
    
    public ArrayList <Exemplaire> getExemplaires() {
        return exemplaires;
    }
    
    public Exemplaire getUnExemplaire (Integer numExemplaire) {
        return 
    }
    
    public ArrayList <Integer> getListNumExemplairesOuvrage() {
        ArrayList <Integer> listNumExemplaires = new ArrayList<>() ;
        for (Exemplaire ex : exemplaires)
            listNumExemplaires.add(ex.getNumExemplaire()) ;
        return listNumExemplaires ;
    }

    public boolean verifAdequationPublic(Integer age, Public publicVise){
        if(publicVise.getAgeMin() <= age){
            return true;
        }
        else{
            return false;
        }
    }
}
