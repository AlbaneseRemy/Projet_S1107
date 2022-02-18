/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modele;

import java.util.Date;

/**
 *
 * @author albanesr
 */
public class Exemplaire{
    private Date dateRecep;
    private Boolean estEmpruntable;
    private final Integer numExemplaire;
    
    public Exemplaire(Date dateRecep, boolean estEmpruntable){
        this.dateRecep=dateRecep;
        this.estEmpruntable=estEmpruntable;
        this.numExemplaire = 1; //A MODIFIER POUR RAJOUTER L'INCRÉMENTATION
    }

    public Date getDateRecep() {
        return dateRecep;
    }

    public Integer getNumExemplaire() {
        return numExemplaire;
    }
    
    
    
}
