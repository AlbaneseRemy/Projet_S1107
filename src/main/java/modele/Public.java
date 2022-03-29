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
