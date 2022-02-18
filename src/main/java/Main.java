
import modele.Bibliotheque;
import util.Persisteur;
import java.io.IOException;
import vue.IHM;

public class Main {

public static final int EXIT_ERR_LOAD = 2;
public static final int EXIT_ERR_SAVE = 3;

public static void main(String[] args) {
    Bibliotheque bibliotheque = null;

    try {
        bibliotheque = Persisteur.lireEtat();
    } catch (ClassNotFoundException | IOException ignored) {
        System.err.println("Erreur irrécupérable pendant le chargement de l'état : fin d'exécution !");
        System.err.flush();
        System.exit(Main.EXIT_ERR_LOAD);
    }

    IHM ihm = new IHM(bibliotheque);

    ihm.afficherInterface();

    try {
        Persisteur.sauverEtat(bibliotheque);
    } catch (IOException ignored) {
        System.err.println("Erreur irrécupérable pendant la sauvegarde de l'état : fin d'exécution !");
        System.err.flush();
        System.exit(Main.EXIT_ERR_SAVE);
    }
}
}
