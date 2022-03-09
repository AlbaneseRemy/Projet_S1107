/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modele;

import java.time.LocalDate;

/**
 *
 * @author albanesr
 */
public class Exemplaire {
    
    // Attributs
    private LocalDate dateRecep;
    private Boolean estEmpruntable;
    private final Integer numExemplaire;
    private Ouvrage ouvrage;
    
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
    
    
    
}
