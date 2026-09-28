package com.kastit.zakaria.view;
import com.kastit.zakaria.business.*;
import java.util.Scanner;

/**
 * Représente l'interface utilisateur (View) du jeu puissnace4
 */
public class MainView {
    /**
     * Constructeur privé pour empêcher d'instancier cette classe.
     */
    private MainView() {
        // Constructeur privé
    }
    /**
     * la méthode qui permet de lancer le projet puissance4
     * on initialise et lance la boucle principal du jeu puissance4
     * @param args reçoit un tableau d'éléments
     */
    public static void main(String[] args) {
        Puissance4 jeu = new Puissance4();
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Début de la partie de Puissance 4 ---");
        affichierGrille(jeu.getPartie().getGrille());

        while (!jeu.gameIsOver()) {
            Joueur courant = jeu.getPartie().getJoueurCourant();
            System.out.println("Au tour de : " + courant.getNom());
            System.out.print("Entrez le numéro de colonne (0-6) ou 'q' pour abandonner : ");

            String saisie = scanner.next();

            if (saisie.equalsIgnoreCase("q")) {
                jeu.abandonner();
                System.out.println("Le joueur " + courant.getNom() + " a abandonné.");
            } else {
                try {
                    int numColonne = Integer.parseInt(saisie);
                    jeu.jouer(numColonne);
                    affichierGrille(jeu.getPartie().getGrille());
                } catch (NumberFormatException e) {
                    System.out.println(">> Erreur : Veuillez entrer un chiffre valide ou 'q' pour abandonner.");
                } catch (Puissance4Exception e) {
                    System.out.println(">> COUP IMPOSSIBLE : " + e.getMessage());
                } catch (IllegalArgumentException e){
                    System.out.println("Erreur de valeur : "+ e.getMessage());
                }
            }
        }

        System.out.println("\n--- FIN DE PARTIE ---");

        if (jeu.getPartie().isParAbandon()) {
            System.out.println("La partie s'est terminée par un abandon.");
        }

        Joueur gagnant = jeu.getPartie().getGagnant();
        if (gagnant != null) {
            affichierGrille(jeu.getPartie().getGrille());
            System.out.println("BRAVO ! Le vainqueur est : " + gagnant.getNom());
        } else if (!jeu.getPartie().isParAbandon()) {
            System.out.println("Match nul ! La grille est pleine.");
        }
        scanner.close();
    }

    /**
     * affiche l'état actuel de de la grille de jeu dans la console
     * @param grille la {@link Grille} à affficher
     */
    public static void affichierGrille(Grille grille) {
        Jeton[][] plateau = grille.getJetons();

        System.out.println("\n 0 1 2 3 4 5 6");
        System.out.println("---------------");
        for (int i = 0; i < Config.NB_LIGNES; i++) {
            System.out.print("|");
            for (int j = 0; j < Config.NB_COLONNES; j++) {
                if (plateau[i][j] == null) {
                    System.out.print(" .");
                } else {
                    System.out.print(" " + plateau[i][j]);
                }
            }
            System.out.println(" |");
        }
        System.out.println("---------------\n");
    }
}