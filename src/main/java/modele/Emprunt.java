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
public class Emprunt implements Serializable{
    
    // Attributs
    private LocalDate dateEmprunt ;
    private Lecteur lecteur ;
    private Exemplaire exemplaire ;
    
    // Constructeur
    public Emprunt (Lecteur lecteur, Exemplaire exemplaire) {
        lierLecteur(lecteur) ;
        lierExemplaire(exemplaire) ;
        dateEmprunt = LocalDate.now();
    }
    
    private void lierExemplaire (Exemplaire ex) {
        exemplaire = ex;
    }
    
    private void lierLecteur (Lecteur l) {
        lecteur = l;
    }
    
    // Getters
    public Lecteur getLecteur() {
        return lecteur;
    }
    
    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }
    
    public LocalDate getDateRetour() {
        return dateEmprunt.plusDays(8);
    }
    
    public Exemplaire getExemplaire() {
        return exemplaire ;
    }
    
    public Ouvrage getOuvrageExemplaire(){
        return exemplaire.getOuvrage();
    } 
}
