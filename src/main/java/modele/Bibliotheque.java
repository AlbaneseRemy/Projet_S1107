package modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import util.ES;
import vue.*;
import vue.IHM.InfosLecteur;

public class Bibliotheque implements Serializable {

private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
private Map<Integer, Lecteur> lecteurs;  // association qualifiée par le num

public Bibliotheque() {
        this.lecteurs = new HashMap<>();
}

public void nouveauLecteur(IHM ihm) {
    IHM.InfosLecteur infosLecteur = ihm.saisirLecteur();
    Lecteur l = lecteurs.get(infosLecteur.num);
    if (l == null) {
        l = new Lecteur(infosLecteur.num, infosLecteur.nom, infosLecteur.prenom, infosLecteur.dateNaiss, infosLecteur.email);
        lierLecteur(l, infosLecteur.num);
        ihm.informerUtilisateur("création du lecteur de numéro : " + infosLecteur.num, true);

    } else {
        ihm.informerUtilisateur("numéro de lecteur existant", false);
    }
}

public Map<Integer, Lecteur> getLecteurs() {
    return this.lecteurs;
}

private void lierLecteur(Lecteur l, Integer num) {
    this.lecteurs.put(num, l);
}

}
