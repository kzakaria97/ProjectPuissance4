package com.kastit.zakaria.business;

/**
 * class qui permet de gérer le déroulement d'une partie (jouer, abandoner,vérifier l'état de la partie)
 */
public class Puissance4 {
    private Partie puissance4;

    /**
     * constructeur par défaut
     * créer et initialiser une nouvelle partie
     */
    public Puissance4() {
        this.puissance4 = new Partie();
    }

    /**
     * Constructeur qui permet de démarrer une partie
     * @param puissance4 fait réference à {@link Partie} éxistante
     */
    public Puissance4(Partie puissance4) {
        this.puissance4 = puissance4;
    }

    /**
     * retourne la partie en cours
     * @return puissance4
     */
    public Partie getPartie() {
        return puissance4;
    }

    /**
     * vérifie si la partie est terminée
     * @return true si la partie est terminée, sinon retorune false
     */
    public boolean gameIsOver() {
        return puissance4.isPartieFinie();
    }
    /**
     * méthode permetant au joueur courant d'inserer un jeton dans la colonne choisie
     * @param numColonne l'indice de la colonne
     * @throws IllegalStateException si on tente de lancer la méthode alors que la parrtie est terminée
     * @throws Puissance4Exception si on essaie d'inserer un jeton dans une colonne invalide ou pleine
     */
    public void jouer(int numColonne) throws Puissance4Exception  {
        if (gameIsOver()){
            throw new IllegalArgumentException("La partie est terminée, impossible de jouer.");
        }
        Joueur courant = puissance4.getJoueurCourant();
        Grille grille = puissance4.getGrille();
        Jeton jeton = new Jeton(courant.getNom());
        int ligne = grille.insererJeton(jeton, numColonne);
        Position pos = new Position(ligne, numColonne);
        if (grille.alignementRealise(pos)) {
            puissance4.setPartieFinie(true);
            puissance4.setGagnant(courant);
        } else if (grille.isFullGrille()) {
            puissance4.setPartieFinie(true);
            puissance4.setGagnant(null);
        } else {
            changerJoueur();
        }
    }

    /**
     * permet de mettre fin à la partie par abandon, le joueur adverse est alors déclaré vainqueur
     */
    public void abandonner() {
        puissance4.setParAbandon(true);
        puissance4.setPartieFinie(true);
        changerJoueur();
        puissance4.setGagnant(puissance4.getJoueurCourant());
    }

    /**
     * permet de changer le joueur courant afin de passer au joueur adverse
     */
    public void changerJoueur() {
        Joueur[] joueurs = puissance4.getJoueurs();
        if (puissance4.getJoueurCourant() == joueurs[0]) {
            puissance4.setJoueurCourant(joueurs[1]);
        } else {
            puissance4.setJoueurCourant(joueurs[0]);
        }
    }
}