package com.kastit.zakaria.business;

/**
 * permet de gérer l'état de la partie en incluant la class Grille et Joueur
 */

public class Partie {
    private Grille grille;
    private Joueur[] joueurs;
    private Joueur joueurCourant;
    private Joueur gagnant;
    private boolean partieFinie;
    private boolean parAbandon;

    /**
     * Constructeur par défaut
     * initialise une nouvelle grille, crée deux joueurs (JAUNE et ROUGE)
     */
    public Partie() {
        this.grille = new Grille();
        this.joueurs = new Joueur[2];
        this.joueurs[0] = new Joueur(Couleur.JAUNE);
        this.joueurs[1] = new Joueur(Couleur.ROUGE);
        int indiceAleatoire = (int) (Math.random() * 2);
        this.joueurCourant = joueurs[indiceAleatoire];
        this.partieFinie = false;
        this.parAbandon = false;
        this.gagnant = null;
    }

    /**
     * définit la grille de jeu
     * @return la {@link Grille} de la partie
     */
    public Grille getGrille() {
        return grille;
    }

    /**
     * définit la grille de jeu
     * @param grille la nouvelle grille {@link Grille}
     */
    public void setGrille(Grille grille) {
        this.grille = grille;
    }

    /**
     * permet de retourner le tableau des deux joueurs
     * @return le tableau de joueurs
     */
    public Joueur[] getJoueurs() {
        return this.joueurs.clone();
    }

    /**
     * définit le tableau des joueurs
     * @param joueurs le tableau de {@link Joueur}
     */
    public void setJoueurs(Joueur[] joueurs) {
        this.joueurs = joueurs;
    }

    /**
     * retourne le joueur qui doit jouer le prochain coup
     * @return le {@link Joueur} courant
     */
    public Joueur getJoueurCourant() {
        return joueurCourant;
    }
    /**
     * Définit le joueur courant.
     *
     * @param joueurCourant Le nouveau {@link Joueur} courant.
     */
    public void setJoueurCourant(Joueur joueurCourant) {
        this.joueurCourant = joueurCourant;
    }
    /**
     * Vérifie si la partie est terminée (victoire, abandon ou grille pleine).
     * @return true si la partie est finie, false sinon.
     */
    public boolean isPartieFinie() {
        return partieFinie;
    }

    /**
     * définit si la partie est terminée
     * @param partieFinie true si la partie est finie
     */
    public void setPartieFinie(boolean partieFinie) {
        this.partieFinie = partieFinie;
        if (this.partieFinie && !this.parAbandon && this.gagnant == null) {
            this.gagnant = this.joueurCourant;
        }
    }

    /**
     * vérifie si la partie se termine par un abandon
     * @return retourne true si il y a un abandon sinon false
     */
    public boolean isParAbandon() {
        return parAbandon;
    }

    /**
     * définit si la partie s'est terminée par abandon
     * @param parAbandon true si i y a un abandon, sinon false
     */
    public void setParAbandon(boolean parAbandon) {
        this.parAbandon = parAbandon;
    }

    /**
     * permet de retourner le gagnant de la partie (null si match null)
     * @return le joueur gagnant, ou null
     */
    public Joueur getGagnant() {
        return gagnant;
    }

    /**
     * définit le gagant de la partie
     * @param j le gagant de la partie ou null si il y a match null
     */
    public void setGagnant(Joueur j) {
        if (j != null && j != joueurs[0] && j != joueurs[1]) {
            System.out.println("Tentative de fraude : ce joueur n'est pas dans la partie");
            return;
        }

        if (this.partieFinie && !this.parAbandon && j == null) {
            return;
        }
        this.gagnant = j;
    }
    /**
     * permet de retourner un texte par rapport à l'état de la partie
     * @return la chaine définit
     */
    @Override
    public String toString() {
        return "Partie en cours...";
    }
}