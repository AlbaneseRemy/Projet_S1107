/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modele;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author albanesr
 */
public class Ouvrage {
    private String titre;
    private String nomEditeur;
    private Date dateParution;
    private ArrayList<String> nomAuteurs;
    private final String numISBN;
    private Public publicVisé;
    private Integer numDernierExemplaire;
    
    
    public Ouvrage(String titre, String nomEditeur, Date dateParution, ArrayList<String> nomAuteurs, String numISBN, Public publicVisé){
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
    
    public Date dateParution(){
        return dateParution;
    }

    public ArrayList<String> getNomAuteurs() {
        return nomAuteurs;
    }

    public String getNumISBN() {
        return numISBN;
    }

    public Public getPublicVisé() {
        return publicVisé;
    }

    public Integer getNumDernierExemplaire() {
        return numDernierExemplaire;
    }
    
    public void setNumDernierExemplaire(){
        this.numDernierExemplaire += 1;
    }
    
}
