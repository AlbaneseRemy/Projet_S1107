/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modele;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author albanesr
 */
public class Ouvrage {
    private String titre;
    private String nomEditeur;
    private LocalDate dateParution;
    private ArrayList<String> nomAuteurs;
    private final String numISBN;
    private Public publicVisé;
    private Integer numDernierExemplaire;
    
    
    public Ouvrage(String titre, String nomEditeur, LocalDate dateParution, ArrayList<String> nomAuteurs, String numISBN, Public publicVisé){
        this.titre = titre;
        this.nomEditeur = nomEditeur;
        this.nomAuteurs = nomAuteurs;
        this.numISBN=numISBN;
        this.publicVisé = publicVisé;
    }
    
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
        return publicVisé;
    }

    public Integer getNumDernierExemplaire() {
        return numDernierExemplaire;
    }
    
    public void setNumDernierExemplaire(){
        this.numDernierExemplaire += 1;
    }
    
}
