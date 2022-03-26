/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package modele;


public enum Public {
    ENFANT(3),
    ADO(10),
    ADULTE(16);
    private int ageMin;
    
    Public(int ageMin){
        this.ageMin = ageMin;
    }
    
    public int getAgeMin(){
        return this.ageMin;
    }
}
