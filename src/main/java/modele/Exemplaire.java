/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modele;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author albanesr
 */
public class Exemplaire implements Serializable{
    
    // Attributs
    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
    private LocalDate dateRecep;
    private Boolean estEmpruntable;
    private final Integer numExemplaire;
    private Ouvrage ouvrage;
    private Emprunt emprunt;
    
    // Constructeur
    public Exemplaire(LocalDate dateRecep, Boolean estEmpruntable, Ouvrage ouvrage) {
        this.dateRecep = dateRecep ;
        this.estEmpruntable = estEmpruntable ;
        this.lierOuvrage(ouvrage) ;
        this.numExemplaire = this.ouvrage.getNumDernierExemplaire() ;
    }

    private void lierOuvrage (Ouvrage ouvrage) {
        this.ouvrage = ouvrage ;
    }

    // Getters
    public LocalDate getDateRecep() {
        return dateRecep;
    }
    
    public Integer getNumExemplaire() {
        return numExemplaire;
    }
    
    public boolean estDisponible(){
        return (estEmpruntable == true && emprunt == null) ;
    }

    public boolean estEmprunte()
    {
        return (estEmpruntable == true && emprunt != null) ;
    }

    public Lecteur getLecteur() {
        return getEmprunt().getLecteur() ;
    }
    
    public Ouvrage getOuvrage() {
        return ouvrage ;
    }

    public Emprunt getEmprunt() {
        return emprunt ;
    }

    public void retirerEmprunt() {
        emprunt = null ;
    }
    
    public void lierEmprunt (Emprunt em) {
        emprunt = em;
    }
}
