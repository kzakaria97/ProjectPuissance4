package com.kastit.zakaria.business;
/**
 * Représente une exception de jeu (coup illégal, colonne pleine, etc.) spécifique à Puissance 4.
 */
public class Puissance4Exception extends Exception {
    /**
     * @param numColonne représente l'indice de la colonne joué
     * @param AUCUNE_COLONNE Constante utilisée pour les erreurs qui ne sont pas liées à une colonne spécifique.
     * @param RAISON_COLONNE_PLEINE constante pour identifié les erreurs liées à une colonne pleine
     * @param RAISON_INATTENDUE constante pour indentifier les erreurs inattendues
     */
    private final int numColonne;
    public static final int AUCUNE_COLONNE = -1;
    public static final String RAISON_COLONNE_PLEINE = "COLONNE PLEINE";
    public static final String RAISON_INATTENDUE = "ERREUR INATTENDUE";


    /**
     * Constructeur utilisé pour les erreurs de jeu qui nécessite un numéro de colonne.
     * Le message d'erreur est construit en utilisant la raison et l'indice de colonne.
     * @param raison L'information concernant l'erreur (ex: "COLONNE PLEINE", "HORS LIMITES").
     * @param numColonne L'indice de la colonne qui a causé l'erreur.
     */
    public Puissance4Exception(String raison, int numColonne) {
        super("Coup non accordé sur la colonne " + numColonne + ". Raison : " + raison + ".");
        this.numColonne = numColonne;
    }

    /**
     * Constructeur pour les erreurs générales (sans colonne spécifique, ex: partie terminée).
     * @param message Le message d'erreur complet.
     */
    public Puissance4Exception(String message) {
        this(message, AUCUNE_COLONNE);
    }

    /**
     * Retourne l'indice de la colonne concernée par l'erreur.
     * @return L'indice de la colonne, ou -1 si l'erreur est générale.
     */
    public int getNumColonne() {
        return numColonne;
    }
}