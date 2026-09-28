package com.kastit.zakaria.business;
/**
 * cette class permet de représenter un jeton sur la grille tout en étant associé à une couleur
 */
public class Jeton {
    /**
     * La couleur du jeton (JAUNE ou ROUGE).
     * @see Couleur récuperer depuis l'enum
     */
    public Couleur couleur;
    /**
     * constructeur de la class jeton avec une couleur spécifié
     * @param couleur la couleur du jetoon {@link Couleur}
     */
    public Jeton(Couleur couleur) {
        this.couleur = couleur;
    }
    /**
     * permet de retourner la couleur du jeton
     * @return la couleur du jeton
     */
    public Couleur getCouleur() {
        return couleur;
    }
    /**
     * permet de retourner un texte (R ou J) qui représente le jeton
     * @return "J" si la couleur est JAUNE, sinon retourne R
     */
    @Override
    public String toString() {
        if(this.couleur==Couleur.JAUNE){
            return "J";
        }else {
            return "R";
        }
    }
}