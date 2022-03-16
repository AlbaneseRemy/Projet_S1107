package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Set;
import org.apache.commons.validator.routines.EmailValidator;

/**
* Classe de gestion de primitives d'EntréesSorties (affichages et saisies)

*/
public class ES {

    private static final long serialVersionUID = 1L ;
    private static BufferedReader entree = new BufferedReader(new InputStreamReader(System.in)) ;

    /**
    * Constructeur. Aucun traitement requis.
    */
    public ES() {
    }

    //	----------------------------------------------------------------------------------------------------------------
    //	Gestion des chaines de caractère
    /**
    * permet la saisie d'une chaine. Pas de contrôle du format saisi.
    *
    * @return chaine saisie par l'utilisateur
    */
    public static String lireChaine() {
        try {
            return entree.readLine() ;
        } catch (IOException e) {
            System.out.println(" ERREUR d'E/S : IO.LIRE_CHAINE") ;
            return "" ;
        }
    }  // Fin lireChaine

    /**
    * Affiche un libellé et permet la saisie d'une chaine.
    *
    * @param libelle : libellé à afficher en début pour indiquer quelle saisie effectuer
    * @return String : chaine saisie par l'utilisateur
    */
    public static String lireChaine (String libelle) {
        ES.afficherLibelle(libelle) ;
        return lireChaine() ;
    }  // Fin lireChaine

    public static String lireEmail() {
        boolean OK = false ;
        String email = lireChaine() ;
        EmailValidator validator = EmailValidator.getInstance(false, false) ;
        do {
            if (validator.isValid(email)){
                OK = true ;
            }
            else {
            System.out.println(" l'adresse mail n'est pas valide. Recommencez.") ;
            email = lireChaine() ;
            }
        } while (!OK) ;
        return  email ;
    }   // Fin lireEmail

    public static String lireEmail(String libelle) {
        ES.afficherLibelle(libelle) ;
        return lireEmail() ;
    }  // Fin de lireEmail

    //	---------------------------------------------------------------------------------------------------------------------
    //	Gestion des entiers
    /**
    * permet la saisie d'un entier. Si l'utilisateur saisit une valeur
    * non-entière, affichage d'une erreur et demande d'une autre saisie.
    *
    * @return int : l'entier saisi.
    *
    */
    public static int lireEntier() {
        boolean ok = false ;
        int valentiere = 0 ;

        try {
            do {
                try {
                    valentiere = Integer.parseInt(entree.readLine()) ;
                    ok = true;
                } catch (NumberFormatException e) {
                    {System.out.println("Non un entier. Recommencez.");}
                }
            } while (!ok) ;

            return valentiere ;
        } catch (IOException e) {
             System.out.println(" ERREUR d'E/S : IO.LIRE_ENTIER") ;
            return 0 ;
        }
    } // Fin de lireEntier

    /**
    * Affiche un libellé puis permet la saisie d'un entier. Si l'utilisateur
    * saisit une valeur non-entière, affichage d'une erreur et demande d'une
    * autre saisie.
    *
    * @param libelle : libellé à afficher en début pour indiquer quelle saisie
    * effectuer
    * @return int : l'entier saisi.
    *
    */
    public static int lireEntier(String libelle) {
        ES.afficherLibelle(libelle) ;
        return lireEntier() ;
    }  // Fin de lireEntier

    //	----------------------------------------------------------------------------------------------------------------
    //	Gestion des flottants
    /**
    * permet la saisie d'un réel. Si l'utilisateur saisit une valeur non-réelle, affichage d'une erreur et demande d'une
    * autre saisie.
    *
    * @return float : la valeur flottante saisie.
    */
    public static float lireFlottant () {
        boolean ok = false ;
        float valflottant = 0 ;
        Float flot ;
        try {
            do {
                try {
                    flot = Float.valueOf(entree.readLine()) ;
                    valflottant = flot ;
                    ok = true ;
                } catch (NumberFormatException e) {
                    {System.out.println("Non un flottant. Recommencez.");}
                }
            } while (!ok) ;
            return valflottant ;
        } catch (IOException e) {
            System.out.println(" ERREUR d'E/S : IO.LIRE_ENTIER") ;
            return 0 ;
        }
    } // Fin de lireFlottant

    /**
    * Affiche un libellé puis permet la saisie d'un réel. Si l'utilisateur saisit une valeur non-réelle, affichage d'une
    * erreur et demande d'une autre saisie.
    *
    * @param libelle : libellé à afficher en début pour indiquer quelle saisie
    * effectuer
    * @return float : la valeur flottante saisie.
    */
    public static float lireFlottant (String libelle) {
        ES.afficherLibelle(libelle) ;
        return lireFlottant() ;
    }  // Fin de lireFlottant

    //	----------------------------------------------------------------------------------------------------------------
    //	Gestion des dates
    /**
    * permet la saisie d'une date au format AAAA-MM-JJ. Si l'utilisateur saisit une valeur non conforme, affichage d'une
    * erreur et demande d'une autre saisie.
    * @return DateLocal : la date est saisie.
    */

    public static LocalDate lireDate () {
        Boolean OK = false ;
        LocalDate date = null ;
        do {
          try {
             String dateSaisie = lireChaine() ;
             date = LocalDate.parse(dateSaisie) ;
             OK = true ;
            } catch (DateTimeParseException ignored) {
                {System.out.println ("La date saisie n'est pas valide, Recommencez au format YYYY-MM-JJ");}
            }
        } while (!OK) ;
        return date ;
    } // Fin de lireDate

    public static LocalDate lireDate (String libelle) {
        ES.afficherLibelle(libelle) ;
        return lireDate() ;
    }   // Fin de lireDate

    // Traitements internes
    /**
    * @return un entier représentant l'année courante.
    */
    private static int anneeCourante() {
        // Instancie une variable date
        LocalDate dateCourante = LocalDate.now () ;
        // Extrait l'année de cette variable et la renvoie
        return dateCourante.getYear() ;
    }

    /**
    * @return un entier représentant le mois courant.
    */
    private static int moisCourant() {
        // Instancie une variable date
        LocalDate dateCourante = LocalDate.now () ;
        // Extrait le mois de cette variable et la renvoie
        return dateCourante.getMonthValue() ;
    }
    
    private static boolean dateValide(LocalDate verif){
        LocalDate dateCourante = LocalDate.now();
        // Ajouter condition de vérification de la date
        return true;
    }

    //	----------------------------------------------------------------------------------------------------------------
    //	Affichage d'informations
    /**
    * Affichage d'un titre. On fait précéder et suivre le texte reçu par une ligne blanche.
    *
    * @param titre titre à afficher
    */
    public static void afficherTitre (String titre) {
        System.out.println() ;
        System.out.println(titre) ;
        System.out.println() ;
        System.out.flush() ;
    }

    /**
    * Affichage d'un message. On revient à la ligne après le message.
    *
    * @param libelle libelle à afficher.
    */
    public static void afficherLibelle (String libelle) {
        System.out.println(libelle) ;
        System.out.flush() ;
    }

    public static void afficherSetInt(Set<Integer> list, String libelle){
        System.out.println(libelle);
        System.out.println(list);
        System.out.flush();
    }
    
    public static void afficherSetStr(Set<String> list, String libelle){
        System.out.println(libelle);
        System.out.println(list);
        System.out.flush();
    }
    
} // Fin de classe ES
