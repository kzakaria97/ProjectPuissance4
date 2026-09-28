package com.kastit.zakaria.business;
/**
 * Représente la grille de jeu du Puissance 4
 * Gère l'état du plateau, l'insertion des jetons et la vérification des alignements gagnants.
 */
public class Grille {
    /**
     * Retourne le nombre de lignes dans la grille, basé sur {@link Config}.
     */
    public static final int NB_LIGNES = Config.NB_LIGNES;
    /**
     * Retourne le nombre de colonnes dans la grille, basé sur {@link Config}.
     */
    public static final int NB_COLONNES = Config.NB_COLONNES;
    private Jeton[][] plateauDeJeu;

    /**
     * Constructeur par défaut.
     * Initialise une nouvelle grille de jeu vide (NB_LIGNES x NB_COLONNES).
     */
    public Grille(){

        this.plateauDeJeu = new Jeton[NB_LIGNES][NB_COLONNES];
    }
    /**
     * Constructeur permettant d'initialiser la grille
     *
     * @param plateauDeJeu est Le tableau de jetons représentant l'état initial du plateau
     */
    public Grille(Jeton[][] plateauDeJeu){
        this.plateauDeJeu = plateauDeJeu;
    }
    /**
     * Retourne le tableau de jetons représentant l'état actuel du plateau
     * @return Le tableau de jetons
     */
    public Jeton[][] getJetons(){
        return plateauDeJeu;
    }
    public Jeton getJeton(Position pos) {
        if (pos != null && estDansLaGrille(pos.getLigne(), pos.getColonne())) {
            return plateauDeJeu[pos.getLigne()][pos.getColonne()];
        }
        return null;
    }
    /**
     * Insère un jeton dans la colonne indiquée, le jeton tombe à la position la plus basse disponible.
     * @param jeton Le jeton à insérer.
     * @param numColonne L'indice de la colonne (entre 0 et {@link Grille#NB_COLONNES} - 1).
     * @return L'indice de la ligne où le jeton a été inséré
     * @throws IllegalArgumentException Si le jeton fourni est incorrect
     * @throws Puissance4Exception Si la colonne est invalide (hors limites) ou si la colonne est déjà pleine.
     */
    public int insererJeton(Jeton jeton, int numColonne) throws Puissance4Exception {
        if (jeton == null) {
            throw new IllegalArgumentException("Le jeton ne peut pas être null");
        }
        if (numColonne < 0 || numColonne >= NB_COLONNES) {
            throw new IllegalArgumentException("Colonne invalide : l'indice doit être compris entre 0 et "+(NB_COLONNES-1));
        }
        if (plateauDeJeu[0][numColonne] != null) {
            throw new Puissance4Exception(Puissance4Exception.RAISON_COLONNE_PLEINE, numColonne);
        }

        for (int i = NB_LIGNES - 1; i >= 0; i--) {
            if (plateauDeJeu[i][numColonne] == null) {
                plateauDeJeu[i][numColonne] = jeton;
                return i;
            }
        }
        throw new Puissance4Exception(Puissance4Exception.RAISON_INATTENDUE, numColonne);
    }
    /**
     * Vérifie si une colonne spécifique est pleine
     * @param numColonne L'indice de la colonne à vérifier
     * @return {@code true} si la colonne est pleine, {@code false} sinon
     */
    public boolean isFullColonne(int numColonne){
        return plateauDeJeu[0][numColonne] != null;
    }

    /**
     * Vérifie si la grille entière est pleine
     * @return true si toutes les colonnes sont pleines, sinon retourne false
     */
    public boolean isFullGrille(){
        for (int i = 0; i < NB_COLONNES; i++) {
            if (plateauDeJeu[0][i]==null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Vérifie si les coordonéés (ligne,colonne) sont correct et à l'intérieur de la grille
     * @param ligne l'indice de la ligne
     * @param colonne l'indice de la colonne
     * @return true si la position est dans la grille, sinon retourne false
     */
    private boolean estDansLaGrille(int ligne, int colonne) {
        if(ligne >= 0 && ligne < NB_LIGNES && colonne >= 0 && colonne < NB_COLONNES){
            return true ;
        }
        return false;
    }

    /**
     * Permet de compter le nombre de jeton alignés de la même couleur dans une direction
     * soit horizontal, soit vertical ou soit diagonal
     * @param lig l'indice de la ligne de départ
     * @param col l'indice de la colonne de départ
     * @param directionLigne le décalage de la ligne pour la direction
     * @param directionColonne le décalage de la colonne pour la direction
     * @param couleur la couleur du jeton
     * @return retourne true si un alignement d'au moins 4 jetons est compté
     */
    private boolean compteAlignement(int lig, int col, int directionLigne, int directionColonne, Couleur couleur) {
        int compteur = 1;

        int i = 1;
        while (estDansLaGrille(lig + i * directionLigne, col + i * directionColonne) &&
                plateauDeJeu[lig + i * directionLigne][col + i * directionColonne] != null &&
                plateauDeJeu[lig + i * directionLigne][col + i * directionColonne].getCouleur() == couleur) {
            compteur++;
            i++;
        }

        i = 1;
        while (estDansLaGrille(lig - i * directionLigne, col - i * directionColonne) &&
                plateauDeJeu[lig - i * directionLigne][col - i * directionColonne] != null &&
                plateauDeJeu[lig - i * directionLigne][col - i * directionColonne].getCouleur() == couleur) {
            compteur++;
            i++;
        }

        if(compteur>=4){
            return true;
        }else{
            return false;
        }
    }
    /**
     * Vérifie si un alignement gagnant est réalisé autour de la position donnée
     * le controle de l'alignement est effectué dans les 4 directions
     * @param position la position du jeton joué
     * @return retourne true si un alignement de 4 jetons ou plus est trouvé par rapport au dernier jeton joué sinon retourne false
     */
    public boolean alignementRealise(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("La position ne peut pas être null.");
        }
        int ligne = position.getLigne();
        int colonne = position.getColonne();

        if (plateauDeJeu[ligne][colonne] == null) {
            return false;
        }

        Couleur couleurJoue = plateauDeJeu[ligne][colonne].getCouleur();

        if (compteAlignement(ligne, colonne, 0, 1, couleurJoue)){
            return true;
        }
        if (compteAlignement(ligne, colonne, 1, 0, couleurJoue)){
            return true;
        }
        if (compteAlignement(ligne, colonne, 1, 1, couleurJoue)){
            return true;
        }
        if (compteAlignement(ligne, colonne, 1, -1, couleurJoue)){
            return true;
        }
        return false;
    }
}