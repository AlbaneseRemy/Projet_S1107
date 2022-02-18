package modele;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;

public class Lecteur implements Serializable {

private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
private final Integer num;
private String nom;
private String prenom;
private LocalDate dateNaiss;
private String email;

public Lecteur(Integer num, String nom, String prenom, LocalDate dateNaiss, String email) {
    this.num = num;
    this.nom = nom;
    this.prenom = prenom;
    this.dateNaiss = dateNaiss;
    this.email = email;
}

public Integer getNum() {
    return num;
}

public String getNom() {
    return nom;
}

public String getPrenom() {
    return prenom;
}

public LocalDate getDateNaiss() {
    return dateNaiss;
}

public String getEmail() {
    return this.email;
}

public Integer getAge() {
    int age;
    LocalDate dateNaissComp;
    LocalDate dateActuelle = LocalDate.now();
    dateNaissComp = LocalDate.of(dateActuelle.getYear(), dateNaiss.getMonthValue(), dateNaiss.getDayOfMonth());
    if (dateNaissComp.isBefore(dateActuelle)) {
        age = dateActuelle.getYear() - dateNaiss.getYear();
    } else {
        age = dateActuelle.getYear() - dateNaiss.getYear() - 1;
    }
    return age;
}
}
