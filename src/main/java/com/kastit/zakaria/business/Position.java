package com.kastit.zakaria.business;
import java.util.Objects;
/**
 * représente les coordonnées d'une case sur la grille de jeu
 * les lignes sont numérotées de 0 (haut) à NB_LIGNES (bas)
 * les colonnes sont numérotées de 0 (gauche) à NB_COLONNES (droite)
 */
public class Position {
    private int ligne;
    private int colonne;

    /**
     * représente le constructeur d'une position
     * @param ligne l'indice de la grille
     * @param colonne l'indice de la colonne
     */
    public Position(int ligne,int colonne){
        this.ligne=ligne;
        this.colonne=colonne;
    }

    /**
     * retourne l'indice de la ligne
     * @return la ligne
     */
    public int getLigne() {

        return ligne;
    }

    /**
     * retourne l'indice de la colonne
     * @return la colonne
     */
    public int getColonne(){

        return colonne;
    }

    /**
     * permet de retourner un texte sur la position
     * @return une chaine
     */
    @Override
    public String toString() {
        return "Position{" + "ligne=" + ligne + ", colonne=" + colonne + '}';
    }

    /**
     * vérifie si un objet est égal à un autre objet (comparaison faite par rapport aux lignes colonnes)
     * @param o   the reference object with which to compare.
     * @return rertourne true si les positions sont identiques, sinon false
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return ligne == position.ligne && colonne == position.colonne;
    }
}