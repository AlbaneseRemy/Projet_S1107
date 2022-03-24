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
public class Emprunt {

    // Attributs
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;
    private Exemplaire exemplaire;
    private Lecteur lecteur;
    
    public Emprunt() {
        
    }

    public Lecteur getLecteur() {
        return lecteur ;
    }
}
