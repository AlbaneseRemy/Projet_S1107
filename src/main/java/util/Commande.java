package util;

import java.util.HashMap;
import java.util.Map;

/**
* L'enum Commande répertorie les actions que l'utilisa·teur/trice peut
* entreprendre via l'IHM.
*
*/
public enum Commande {

QUITTER(0, "Quitter"),
CREER_LECTEUR(1, "Créer un nouvel lecteur"),
CONSULTER_LECTEURS (2,"Afficher tous les lecteurs");

private final int code;
private final String description;

private static final Map<Integer, Commande> valueByCode = new HashMap<>();

private Commande(int code, String description) {
    assert code >= 0;
    this.code = code;
    this.description = description;
}
static {
    // On initialise une fois pour la durée de vie de l'application le
    // cache de la fonction `valueOfCode`
    for (Commande cmd: Commande.values()) {
        Commande.valueByCode.put(cmd.code, cmd);
    }
}

/**
 * Renvoie la variante de la classe enum dont le code est donné en
 * paramètre.
 *
 * @param code Le code de la variante à retourner.
 *
 * @return La variante de la classe enum dont le code est celui spécifié.
 */
public static final Commande valueOfCode(int code) {
    Commande result = Commande.valueByCode.get(code);
    if (result == null) {
        throw new IllegalArgumentException("Invalid code");
    }
    return result;
}

/** Construit le synopsis des commandes. 
* @return Une chaîne de caractères contenant le synopsis des commandes.
*/
public static String synopsisCommandes() {
    StringBuilder builder = new StringBuilder();

    for (Commande cmd: Commande.values()) {
        builder.append("  ");  // légère indentation
        builder.append(cmd.synopsis());
        builder.append(System.lineSeparator());
    }

    return builder.toString();
}
/**
 * Renvoie le synopsis mis en forme de la commande.
 *
 * @return Une chaîne de caractères sans retour à la ligne contenant le
 *     synopsis de la commande.
 */
private String synopsis() {
    return this.code + " — " + this.description;
};

public static Commande lireCommande() {
    String token = null;
    Boolean OK = false;
    Commande cmd = null;
    do {
        try {
        token = ES.lireChaine();
        int cmdId = Integer.parseUnsignedInt(token);  // may throw NumberFormatException
        cmd = Commande.valueOfCode(cmdId);  // may throw IllegalArgumentException
        OK = true;
    }
    // NumberFormatException est une sous-classe de IllegalArgumentException
    catch (IllegalArgumentException ignored) {
      ES.afficherLibelle("Choix non valide : merci d'entrer un identifiant existant.");
//          result = Optional.empty();
    }
    } while (!OK);
    return cmd;
}

}
