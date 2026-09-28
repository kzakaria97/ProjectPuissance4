package com.kastit.zakaria.business;
/**
 * Représente un joueur dans la partie déterminé par sa couleur
 */
public class Joueur {
    private Couleur nom;
    /**
     * Constructeur de la class Joueur où la couleur définie le nom
     * @param nom la couleur {@link Couleur} associé au joueur
     */
    public Joueur(Couleur nom) {
        this.nom = nom;
    }
    /**
     * retourne le nom du joueur
     * @return la {@link Couleur} du joueur
     */
    public Couleur getNom() {
        return nom;
    }
}