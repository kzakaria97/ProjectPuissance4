package com.test.kastit.zakaria;

import com.kastit.zakaria.business.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class GrilleTest {
    private Grille grille;
    private static final Jeton R = new Jeton(Couleur.ROUGE);
    private static final Jeton J = new Jeton(Couleur.JAUNE);
    private Jeton[][] plateau = new Jeton[Config.NB_LIGNES][Config.NB_COLONNES];


    @BeforeEach
    void setUp(){
        this.grille =  new Grille();
        this.plateau = new Jeton[Config.NB_LIGNES][Config.NB_COLONNES];
    }

    //---------------INSERERJETON-------------
    //----------------CAS POSTIF--------------
    @Test
    @DisplayName("Cas n°1 : insererJeton")
    void testInsertionSimple() throws Puissance4Exception{
        int ligne = grille.insererJeton(R,3);
        assertEquals(5,ligne,"Le jeton doit tomber à la ligne 5");
        assertEquals(R,grille.getJetons()[5][3]);
    }
    @Test
    @DisplayName("Cas n°2 : insererJeton")
    void testEmpilement() throws Puissance4Exception{
        grille.insererJeton(R,3);
        int ligne = grille.insererJeton(J,3);
        assertEquals(4,ligne,"le jeton empilé doit être à la ligne 4");
        assertEquals(J,grille.getJetons()[4][3]);
    }
    @Test
    @DisplayName("Cas n°3 : insererJeton")
    void testExtremitesGauche(){
        assertDoesNotThrow(()->grille.insererJeton(R,0));

        assertEquals(R,grille.getJetons()[5][0]);
    }
    @Test
    @DisplayName("Cas n°4 : insererJeton")
    void testExtremitesDroite(){
        assertDoesNotThrow(()-> grille.insererJeton(R,6));
        assertEquals(R,grille.getJetons()[5][6]);
    }
    @Test
    @DisplayName("Cas n°5 : insererJeton")
    void testRemplissageColonne() throws Puissance4Exception{
        for(int i = 5; i>=0; i--){
            int ligne = grille.insererJeton(R,3);
            assertEquals(i,ligne,"l'indice doit remonter progressivement");
        }
        assertTrue(grille.isFullColonne(3));
    }
    //---------------CAS NEGATIF INSERERJETON-------------------
    @Test
    @DisplayName("Cas n°1 : insererJeton")
    void testHorsChamp(){
        assertThrows(IllegalArgumentException.class,()-> {
            grille.insererJeton(R, 7);
            });
    }
    @Test
    @DisplayName("Cas n°2 : insererJeton")
    void testColonnePleineException() throws Puissance4Exception{
        for(int i=0;i<Config.NB_LIGNES;i++){
            grille.insererJeton(R,3);
        }
        assertThrows(Puissance4Exception.class,()->{
            grille.insererJeton(R,3);
        },"Doit lever une exception si la calonne est plaine ");
    }
    @Test
    @DisplayName("Cas n°3 : insererJeton")
    void testPositionNegatif(){
        assertThrows(IllegalArgumentException.class, ()-> {
            grille.insererJeton(R,-3);
        });
    }
    @Test
    @DisplayName("Cas n°4 : insererJeton")
    void testJetonInvalide(){
        assertThrows(IllegalArgumentException.class, ()->
                grille.insererJeton(null,3));
    }
    @Test
    @DisplayName("Cas négatif n°7 : insererJeton")
    void testJouerApresFin() throws Puissance4Exception {
        Puissance4 jeu = new Puissance4();
        jeu.getPartie().setPartieFinie(true);

        Exception e = assertThrows(IllegalArgumentException.class, ()-> jeu.jouer(2));
        assertEquals("La partie est terminée, impossible de jouer.", e.getMessage());
    }
    @Test
    @DisplayName("isFullGrille - détection grille pleine")
    void testIsFullGrille() throws Puissance4Exception {
        assertFalse(grille.isFullGrille());
        for (int i = 0; i < Config.NB_COLONNES - 1; i++) {
            for (int j = 0; j < Config.NB_LIGNES; j++) {
                grille.insererJeton(R, i);
            }
        }
        for (int i = 0; i < Config.NB_LIGNES - 1; i++) {
            grille.insererJeton(J, 6);
        }
        assertFalse(grille.isFullGrille());
        grille.insererJeton(R, 6);
        assertTrue(grille.isFullGrille());
    }

    //-------------ALIGNEMENTREALISE--------------
    //----------------CAS POSITIF----------------

    @Test
    @DisplayName("Cas de départ : alignementRealise avec position null")
    void testAlignementPositionNull() {
        assertThrows(IllegalArgumentException.class, () -> grille.alignementRealise(null));
    }
    //---------------HORIZONTAL----------------
    @Test
    @DisplayName("Cas n°1 : alignementRealise ")
    void testAlignementRealiseHorizontalHautGauche(){
        plateau[0][0] = R; plateau[0][1] = R; plateau[0][2] = R; plateau[0][3] = R;
        plateau[1][0] = J; plateau[1][1] = J; plateau[1][2] = R; plateau[1][3] = R;
        plateau[2][0] = R; plateau[2][1] = R; plateau[2][2] = J; plateau[2][3] = J;
        plateau[3][0] = J; plateau[3][1] = R; plateau[3][2] = J; plateau[3][3] = R;
        plateau[4][0] = J; plateau[4][1] = R; plateau[4][2] = J; plateau[4][3] = J;
        plateau[5][0] = R; plateau[5][1] = J; plateau[5][2] = R; plateau[5][3] = R;

        Grille grilletest = new Grille(plateau);
        assertTrue(grilletest.alignementRealise(new Position(0,0)));
    }
    @Test
    @DisplayName("Cas n°2 : alignementRealise ")
    void testAlignementRealiseHorizontalHautDroit(){
        plateau[0][3] = R; plateau[0][4] = R; plateau[0][5] = R; plateau[0][6] = R;
        plateau[1][3] = R; plateau[1][4] = J; plateau[1][5] = J; plateau[1][6] = J;
        plateau[2][3] = J; plateau[2][4] = R; plateau[2][5] = J; plateau[2][6] = R;
        plateau[3][3] = J; plateau[3][4] = R; plateau[3][5] = J; plateau[3][6] = J;
        plateau[4][3] = J; plateau[4][4] = J; plateau[4][5] = R; plateau[4][6] = R;
        plateau[5][3] = R; plateau[5][4] = J; plateau[5][5] = J; plateau[5][6] = J;

        Grille grilletest = new Grille(plateau);
        assertTrue(grilletest.alignementRealise(new Position(0,6)));
    }
    @Test
    @DisplayName("Cas n°3 : alignementRealise ")
    void testAlignementRealiseHorizontalHautMilieu(){
        plateau[0][3] = R; plateau[0][4] = R; plateau[0][5] = R; plateau[0][6] = R;
        plateau[1][3] = R; plateau[1][4] = J; plateau[1][5] = J; plateau[1][6] = J;
        plateau[2][3] = J; plateau[2][4] = R; plateau[2][5] = J; plateau[2][6] = R;
        plateau[3][3] = J; plateau[3][4] = R; plateau[3][5] = J; plateau[3][6] = J;
        plateau[4][3] = J; plateau[4][4] = J; plateau[4][5] = R; plateau[4][6] = R;
        plateau[5][3] = R; plateau[5][4] = J; plateau[5][5] = J; plateau[5][6] = J;

        Grille grilletest = new Grille(plateau);
        assertTrue(grilletest.alignementRealise(new Position(0,3)));

    }
    @Test
    @DisplayName("Cas n°4 : alignementRealise")
    void testAlignementRealiseHorizontalBasGauche() {
        plateau[5][0] = R; plateau[5][1] = R; plateau[5][2] = R; plateau[5][3] = R; plateau[5][4] = J; plateau[5][5] = J; plateau[5][6] = J;

        this.grille = new Grille(plateau);
        assertTrue(this.grille.alignementRealise(new Position(5, 0)));
    }
    @Test
    @DisplayName("Cas n°5 : alignementRealise")
    void testAlignementRealiseHorizontalBasDroit() {
        plateau[5][0] = J; plateau[5][1] = J; plateau[5][2] = J; plateau[5][3] = R; plateau[5][4] = R; plateau[5][5] = R; plateau[5][6] = R;

        this.grille = new Grille(plateau);
        assertTrue(this.grille.alignementRealise(new Position(5, 6)));
    }
    @Test
    @DisplayName("Cas n°6 : alignementRealise")
    void testAlignementRealiseHorizontalBasMilieu() {
       /*""""""""""""""""*/plateau[4][2] = J;
        plateau[5][1] = J; plateau[5][2] = J; plateau[5][3] = R; plateau[5][4] = R; plateau[5][5] = R; plateau[5][6] = R;

        this.grille = new Grille(plateau);
        assertTrue(this.grille.alignementRealise(new Position(5, 3)));
    }
    @Test
    @DisplayName("Cas n°7 : alignementRealise")
    void testAlignementRealiseHorizontalMilieuGauche() {
        plateau[2][0] = R; plateau[2][1] = R; plateau[2][2] = R; plateau[2][3] = R;
        plateau[3][0] = J; plateau[3][1] = J; plateau[3][2] = J; plateau[3][3] = R;
        plateau[4][0] = J; plateau[4][1] = R; plateau[4][2] = R; plateau[4][3] = J;
        plateau[5][0] = J; plateau[5][1] = R; plateau[5][2] = R; plateau[5][3] = J;

        this.grille = new Grille(plateau);
        assertTrue(this.grille.alignementRealise(new Position(2, 0)));
    }
    @Test
    @DisplayName("Cas n°8 : alignementRealise")
    void testAlignementRealiseHorizontalMilieuDroit() {
        plateau[2][3] = R; plateau[2][4] = R; plateau[2][5] = R; plateau[2][6] = R;
        plateau[3][3] = J; plateau[3][4] = J; plateau[3][5] = J; plateau[3][6] = R;
        plateau[4][3] = J; plateau[4][4] = R; plateau[4][5] = R; plateau[4][6] = J;
        plateau[5][3] = J; plateau[5][4] = R; plateau[5][5] = R; plateau[5][6] = J;

        this.grille = new Grille(plateau);
        assertTrue(this.grille.alignementRealise(new Position(2, 6)));
    }
    @Test
    @DisplayName("Cas n°9 : alignementRealise")
    void testAlignementRealiseHorizontalMilieuMilieu() {
        plateau[2][3] = R; plateau[2][4] = R; plateau[2][5] = R; plateau[2][6] = R;
        plateau[3][3] = J; plateau[3][4] = J; plateau[3][5] = R; plateau[3][6] = J;
        plateau[4][3] = J; plateau[4][4] = J; plateau[4][5] = R; plateau[4][6] = J;
        plateau[5][3] = R; plateau[5][4] = J; plateau[5][5] = J; plateau[5][6] = R;

        this.grille = new Grille(plateau);
        assertTrue(this.grille.alignementRealise(new Position(2, 3)));
    }
    //--------------VERTICAL--------------
    @Test
    @DisplayName("Cas n°1 : alignementRealise")
    void testAlignementRealiseVerticalHautGauche() {
        plateau[0][0] = R;
        plateau[1][0] = R;
        plateau[2][0] = R;
        plateau[3][0] = R;
        plateau[4][0] = J;
        plateau[5][0] = J; plateau[5][1] = J;

        this.grille = new Grille(plateau);
        assertTrue(this.grille.alignementRealise(new Position(0, 0)));
    }
    @Test
    @DisplayName("Cas n°2 : alignementRealise")
    void testAlignementRealiseVerticalHautMilieu() {
        plateau[0][3] = R;
        plateau[1][3] = R;
        plateau[2][3] = R;
        plateau[3][3] = R;
        plateau[4][3] = J;
        plateau[5][3] = J; plateau[5][4] = J;


        this.grille = new Grille(plateau);
        assertTrue(this.grille.alignementRealise(new Position(0, 3)));
    }
    @Test
    @DisplayName("Cas n°3 : alignementRealise")
    void testAlignementRealiseVerticalHautDroit() {
        /*---------------*/plateau[0][6] = R;
        /*---------------*/plateau[1][6] = R;
        /*---------------*/plateau[2][6] = R;
        /*---------------*/plateau[3][6] = R;
        /*---------------*/plateau[4][6] = J;
        plateau[5][5] = J; plateau[5][6] = J;


        this.grille = new Grille(plateau);
        assertTrue(this.grille.alignementRealise(new Position(0, 6)));
    }
    @Test
    @DisplayName("Cas n°4 : alignementRealise")
    void testAlignementRealiseVerticalMilieuGauche() {
        plateau[2][0] = R;
        plateau[3][0] = R;
        plateau[4][0] = R; plateau[4][1] = J;
        plateau[5][0] = R; plateau[5][1] = J; plateau[5][2] = J;

        this.grille = new Grille(plateau);
        assertTrue(this.grille.alignementRealise(new Position(2, 0)));
    }
    @Test
    @DisplayName("Cas n°5 : alignementRealise")
    void testAlignementRealiseVerticalMilieuMilieu() {
        /*---------------*/plateau[2][2] = R;
        /*---------------*/plateau[3][2] = R;
        /*---------------*/plateau[4][2] = R; plateau[4][3] = J;
        plateau[5][1] = J; plateau[5][2] = R; plateau[5][3] = J;

        this.grille = new Grille(plateau);
        assertTrue(this.grille.alignementRealise(new Position(2, 2)));
    }
    @Test
    @DisplayName("Cas n°6 : alignementRealise")
    void testAlignementRealiseVerticalMilieuDroit() {
        /*---------------*//*---------------*/plateau[2][6] = R;
        /*---------------*//*---------------*/plateau[3][6] = R;
        /*---------------*/plateau[4][1] = J; plateau[4][6] = R;
        plateau[5][0] = J; plateau[5][1] = J; plateau[5][6] = R;

        this.grille = new Grille(plateau);
        assertTrue(this.grille.alignementRealise(new Position(2, 6)));
    }
    //--------------DIAGONAL--------------
    @Test
    @DisplayName("Cas n°1 : alignementRealise")
    void testAlignementRealiseDiagonalHautGaucheDescendant() {
        plateau[0][0] = R;
        plateau[1][0] = R; plateau[1][1] = R;
        plateau[2][0] = J; plateau[2][1] = R; plateau[2][2] = R; plateau[2][3] = J;
        plateau[3][0] = J; plateau[3][1] = R; plateau[3][2] = R; plateau[3][3] = R;
        plateau[4][0] = R; plateau[4][1] = J; plateau[4][2] = J; plateau[4][3] = J;
        plateau[5][0] = J; plateau[5][1] = J; plateau[5][2] = R; plateau[5][3] = J;

        this.grille = new Grille(plateau);
        assertTrue(this.grille.alignementRealise(new Position(0, 0)));
    }
    @Test
    @DisplayName("Cas n°2 : alignementRealise")
    void testAlignementRealiseDiagonalHautGaucheMontant() {
        plateau[0][0] = R;
        plateau[1][0] = R; plateau[1][1] = R;
        plateau[2][0] = J; plateau[2][1] = J; plateau[2][2] = R;
        plateau[3][0] = J; plateau[3][1] = J; plateau[3][2] = J; plateau[3][3] = R;
        plateau[4][0] = R; plateau[4][1] = R; plateau[4][2] = R; plateau[4][3] = J;
        plateau[5][0] = R; plateau[5][1] = J; plateau[5][2] = J; plateau[5][3] = R;

        this.grille = new Grille(plateau);
        assertTrue(this.grille.alignementRealise(new Position(3, 3)));
    }
    @Test
    @DisplayName("Cas n°3 : alignementRealise")
    void testAlignementRealiseDiagonalBasGaucheMontant() {
        /*---------------*//*---------------*//*---------------*/plateau[2][3] = R;
        /*---------------*//*---------------*/plateau[3][2] = R; plateau[3][3] = R;
        /*---------------*/plateau[4][1] = R; plateau[4][2] = J; plateau[4][3] = J;
        plateau[5][0] = R; plateau[5][1] = J; plateau[5][2] = J; plateau[5][3] = R; plateau[5][4] = J;

        this.grille = new Grille(plateau);
        assertTrue(this.grille.alignementRealise(new Position(5, 0)));
    }
    @Test
    @DisplayName("Cas n°4 : alignementRealise")
    void testAlignementRealiseDiagonalBasGaucheDescendant() {
        /*---------------*//*---------------*//*---------------*/plateau[2][3] = R;
        /*---------------*//*---------------*/plateau[3][2] = R; plateau[3][3] = R;
        /*---------------*/plateau[4][1] = R; plateau[4][2] = J; plateau[4][3] = J;
        plateau[5][0] = R; plateau[5][1] = J; plateau[5][2] = J; plateau[5][3] = R; plateau[5][4] = J;

        this.grille = new Grille(plateau);
        assertTrue(this.grille.alignementRealise(new Position(2, 3)));
    }
    @Test
    @DisplayName("Cas n°5 : alignementRealise")
    void testAlignementRealiseDiagonalHautDroitDescendant() {
        /*---------------*//*---------------*//*---------------*/plateau[0][6] = R;
        /*---------------*//*---------------*/plateau[1][5] = R; plateau[1][6] = J;
        /*---------------*/plateau[2][4] = R; plateau[2][5] = J; plateau[2][6] = R;
        plateau[3][3] = R; plateau[3][4] = R; plateau[3][5] = J; plateau[3][6] = R;
        plateau[4][3] = J; plateau[4][4] = J; plateau[4][5] = R; plateau[4][6] = J;
        plateau[5][3] = J; plateau[5][4] = R; plateau[5][5] = R; plateau[5][6] = J;

        this.grille = new Grille(plateau);
        assertTrue(this.grille.alignementRealise(new Position(0, 6)));
    }
    @Test
    @DisplayName("Cas n°6 : alignementRealise")
    void testAlignementRealiseDiagonalHautDroitMontant() {
        /*---------------*//*---------------*//*---------------*/plateau[0][6] = R;
        /*---------------*//*---------------*/plateau[1][5] = R; plateau[1][6] = J;
        /*---------------*/plateau[2][4] = R; plateau[2][5] = J; plateau[2][6] = J;
        plateau[3][3] = R; plateau[3][4] = J; plateau[3][5] = J; plateau[3][6] = R;
        plateau[4][3] = J; plateau[4][4] = R; plateau[4][5] = J; plateau[4][6] = R;
        plateau[5][3] = R; plateau[5][4] = J; plateau[5][5] = R; plateau[5][6] = R;

        this.grille = new Grille(plateau);
        assertTrue(this.grille.alignementRealise(new Position(3, 3)));
    }
    @Test
    @DisplayName("Cas n°7 : alignementRealise")
    void testAlignementRealiseDiagonalBasDroitMontant() {
        plateau[2][3] = R;
        plateau[3][3] = J; plateau[3][4] = R;
        plateau[4][3] = J; plateau[4][4] = J; plateau[4][5] = R;
        plateau[5][3] = J; plateau[5][4] = J; plateau[5][5] = R; plateau[5][6] = R;

        this.grille = new Grille(plateau);
        assertTrue(this.grille.alignementRealise(new Position(5, 6)));
    }
    @Test
    @DisplayName("Cas n°8 : alignementRealise")
    void testAlignementRealiseDiagonalBasDroitDescendant() {
        plateau[2][3] = R;
        plateau[3][3] = J; plateau[3][4] = R;
        plateau[4][3] = J; plateau[4][4] = J; plateau[4][5] = R;
        plateau[5][3] = J; plateau[5][4] = J; plateau[5][5] = R; plateau[5][6] = R;

        this.grille = new Grille(plateau);
        assertTrue(this.grille.alignementRealise(new Position(2, 3)));
    }
    //-------------ALIGNEMENTREALISE--------------
    //----------------CAS NEGATIF----------------

    //-----------trois jeton---------------
    @Test
    @DisplayName("Cas n°1 : alignementRealise")
    void testAlignementRealiseTroisJeton() {
        plateau[0][0] = R; plateau[0][1] = R; plateau[0][2] = R;
        plateau[1][0] = J; plateau[1][1] = R; plateau[1][2] = J;
        plateau[2][0] = J; plateau[2][1] = J; plateau[2][2] = R;
        plateau[3][0] = J; plateau[3][1] = R; plateau[3][2] = J;
        plateau[4][0] = R; plateau[4][1] = J; plateau[4][2] = R;
        plateau[5][0] = J; plateau[5][1] = J; plateau[5][2] = R;

        this.grille = new Grille(plateau);
        assertFalse(this.grille.alignementRealise(new Position(0, 0)));
    }
    //-----------------HORIZONTAL-------------------
    @Test
    @DisplayName("Cas n°1 : alignementRealise")
    void testAlignementRealiseHorizontalHautGaucheFalse() {
        plateau[0][0] = R; plateau[0][1] = R; plateau[0][2] = J; plateau[0][3] = R;
        plateau[1][0] = J; plateau[1][1] = R; plateau[1][2] = J; plateau[1][3] = R;
        plateau[2][0] = J; plateau[2][1] = J; plateau[2][2] = R; plateau[2][3] = J;
        plateau[3][0] = J; plateau[3][1] = R; plateau[3][2] = J; plateau[3][3] = J;
        plateau[4][0] = R; plateau[4][1] = J; plateau[4][2] = R; plateau[4][3] = R;
        plateau[5][0] = J; plateau[5][1] = J; plateau[5][2] = R; plateau[5][3] = J;

        this.grille = new Grille(plateau);
        assertFalse(this.grille.alignementRealise(new Position(0, 0)));
    }
    @Test
    @DisplayName("Cas n°2 : alignementRealise")
    void testAlignementRealiseHorizontalHautMilieuFalse() {
        plateau[0][0] = R; plateau[0][1] = R; plateau[0][2] = J; plateau[0][3] = R;
        plateau[1][0] = J; plateau[1][1] = R; plateau[1][2] = J; plateau[1][3] = R;
        plateau[2][0] = J; plateau[2][1] = J; plateau[2][2] = R; plateau[2][3] = J;
        plateau[3][0] = J; plateau[3][1] = R; plateau[3][2] = J; plateau[3][3] = J;
        plateau[4][0] = R; plateau[4][1] = J; plateau[4][2] = R; plateau[4][3] = R;
        plateau[5][0] = J; plateau[5][1] = J; plateau[5][2] = R; plateau[5][3] = J;

        this.grille = new Grille(plateau);
        assertFalse(this.grille.alignementRealise(new Position(0, 3)));
    }
    @Test
    @DisplayName("Cas n°3 : alignementRealise")
    void testAlignementRealiseHorizontalHautDroiteFalse() {
        plateau[0][3] = R; plateau[0][4] = R; plateau[0][5] = J; plateau[0][6] = R;
        plateau[1][3] = J; plateau[1][4] = J; plateau[1][5] = J; plateau[1][6] = R;
        plateau[2][3] = J; plateau[2][4] = R; plateau[2][5] = R; plateau[2][6] = J;
        plateau[3][3] = R; plateau[3][4] = R; plateau[3][5] = J; plateau[3][6] = J;
        plateau[4][3] = J; plateau[4][4] = J; plateau[4][5] = R; plateau[4][6] = J;
        plateau[5][3] = J; plateau[5][4] = R; plateau[5][5] = J; plateau[5][6] = R;

        this.grille = new Grille(plateau);
        assertFalse(this.grille.alignementRealise(new Position(0, 6)));
    }
    @Test
    @DisplayName("Cas n°4 : alignementRealise")
    void testAlignementRealiseHorizontalBasGaucheFalse() {
        plateau[5][0] = R; plateau[5][1] = R; plateau[5][2] = J; plateau[5][3] = R;

        this.grille = new Grille(plateau);
        assertFalse(this.grille.alignementRealise(new Position(5, 0)));
    }

    @Test
    @DisplayName("Cas n°5 : alignementRealise")
    void testAlignementRealiseHorizontalBasMilieuFalse() {
        plateau[5][3] = R; plateau[5][4] = R; plateau[5][5] = J; plateau[5][6] = R;

        this.grille = new Grille(plateau);
        assertFalse(this.grille.alignementRealise(new Position(5, 3)));
    }
    @Test
    @DisplayName("Cas n°6 : alignementRealise")
    void testAlignementRealiseHorizontalBasDroitFalse() {
        plateau[5][3] = R; plateau[5][4] = R; plateau[5][5] = J; plateau[5][6] = R;

        this.grille = new Grille(plateau);
        assertFalse(this.grille.alignementRealise(new Position(5, 6)));
    }
    @Test
    @DisplayName("Cas n°7 : alignementRealise")
    void testAlignementRealiseHorizontalMilieuGaucheFalse() {
        plateau[2][0] = R; plateau[2][1] = J; plateau[1][2] = J; plateau[2][3] = R;
        plateau[3][0] = R; plateau[3][1] = R; plateau[3][2] = R; plateau[3][3] = J;
        plateau[4][0] = R; plateau[4][1] = J; plateau[4][2] = J; plateau[4][3] = R;
        plateau[5][0] = J; plateau[5][1] = J; plateau[5][2] = R; plateau[5][3] = J;

        this.grille = new Grille(plateau);
        assertFalse(this.grille.alignementRealise(new Position(2, 0)));
    }
    @Test
    @DisplayName("Cas n°8 : alignementRealise")
    void testAlignementRealiseHHorizontalMilieuMilieuFalse() {
        plateau[2][3] = R; plateau[2][4] = J; plateau[2][5] = J; plateau[2][6] = R;
        plateau[3][3] = J; plateau[3][4] = R; plateau[3][5] = J; plateau[3][6] = R;
        plateau[4][3] = R; plateau[4][4] = J; plateau[4][5] = R; plateau[4][6] = J;
        plateau[5][3] = R; plateau[5][4] = J; plateau[5][5] = R; plateau[5][6] = J;

        this.grille = new Grille(plateau);
        assertFalse(this.grille.alignementRealise(new Position(2, 3)));
    }
    @Test
    @DisplayName("Cas n°9 : alignementRealise")
    void testAlignementRealiseHorizontalMilieuDroitFalse() {
        plateau[2][3] = J; plateau[2][4] = R; plateau[2][5] = R; plateau[2][6] = R;
        plateau[3][3] = R; plateau[3][4] = J; plateau[3][5] = R; plateau[3][6] = R;
        plateau[4][3] = J; plateau[4][4] = R; plateau[4][5] = J; plateau[4][6] = J;
        plateau[5][3] = J; plateau[5][4] = J; plateau[5][5] = J; plateau[5][6] = R;

        this.grille = new Grille(plateau);
        assertFalse(this.grille.alignementRealise(new Position(2, 6)));
    }
    //-----------VERTICAL----------
    @Test
    @DisplayName("Cas n°1 : alignementRealise")
    void testAlignementRealiseVerticalHautDroitFalse() {
        /*---------------*//*---------------*//*---------------*/plateau[0][6] = R;
        /*---------------*//*---------------*//*---------------*/plateau[1][6] = R;
        plateau[2][3] = R; plateau[2][4] = J; plateau[2][5] = J; plateau[2][6] = R;
        plateau[3][3] = R; plateau[3][4] = J; plateau[3][5] = R; plateau[3][6] = J;
        plateau[4][3] = J; plateau[4][4] = R; plateau[4][5] = J; plateau[4][6] = J;
        plateau[5][3] = J; plateau[5][4] = J; plateau[5][5] = J; plateau[5][6] = R;

        this.grille = new Grille(plateau);
        assertFalse(this.grille.alignementRealise(new Position(0, 6)));
    }
    @Test
    @DisplayName("Cas n°2 : alignementRealise")
    void testAlignementRealiseVHGF() {
        plateau[0][0] = R;
        plateau[1][0] = R;
        plateau[2][0] = R; plateau[2][1] = R; plateau[2][2] = J; plateau[2][3] = J;
        plateau[3][0] = J; plateau[3][1] = R;;plateau[3][2] = J; plateau[3][3] = R;
        plateau[4][0] = J; plateau[4][1] = J; plateau[4][2] = R; plateau[4][3] = J;
        plateau[5][0] = R; plateau[5][1] = J; plateau[5][2] = J; plateau[5][3] = J;

        this.grille = new Grille(plateau);
        assertFalse(this.grille.alignementRealise(new Position(0, 0)));
    }
    @Test
    @DisplayName("Cas n°3 : alignementRealise")
    void testAlignementRealiseVerticalHautMilieuFalse() {
        /*---------------*//*---------------*//*---------------*/plateau[0][3] = R;
        /*---------------*//*---------------*//*---------------*/plateau[1][3] = R;
        plateau[2][0] = R; plateau[2][1] = R; plateau[2][2] = J; plateau[2][3] = J;
        plateau[3][0] = J; plateau[3][1] = R;;plateau[3][2] = J; plateau[3][3] = R;
        plateau[4][0] = J; plateau[4][1] = J; plateau[4][2] = R; plateau[4][3] = J;
        plateau[5][0] = R; plateau[5][1] = J; plateau[5][2] = J; plateau[5][3] = J;

        this.grille = new Grille(plateau);
        assertFalse(this.grille.alignementRealise(new Position(0, 3)));
    }
    //-----------DIAGONAL----------
    @Test
    @DisplayName("Cas n°1 : alignementRealise")
    void testAlignementRealiseDiagonaleHautGaucheDescendantFalse() {
        plateau[0][0] = R;
        plateau[1][0] = J; plateau[1][1] = R;
        plateau[2][0] = R; plateau[2][1] = R; plateau[2][2] = J;
        plateau[3][0] = J; plateau[3][1] = R; plateau[3][2] = J; plateau[3][3] = R;
        plateau[4][0] = J; plateau[4][1] = J; plateau[4][2] = R; plateau[4][3] = J;
        plateau[5][0] = R; plateau[5][1] = J; plateau[5][2] = J; plateau[5][3] = J;

        this.grille = new Grille(plateau);
        assertFalse(this.grille.alignementRealise(new Position(0, 0)));
    }
    @Test
    @DisplayName("Cas n°2 : alignementRealise")
    void testAlignementRealiseDiagonalHautGaucheMontantFalse() {
        plateau[0][0] = R;
        plateau[1][0] = J; plateau[1][1] = R;
        plateau[2][0] = R; plateau[2][1] = R; plateau[2][2] = J;
        plateau[3][0] = J; plateau[3][1] = R; plateau[3][2] = J; plateau[3][3] = R;
        plateau[4][0] = J; plateau[4][1] = J; plateau[4][2] = R; plateau[4][3] = J;
        plateau[5][0] = R; plateau[5][1] = J; plateau[5][2] = J; plateau[5][3] = J;

        this.grille = new Grille(plateau);
        assertFalse(this.grille.alignementRealise(new Position(3, 3)));
    }
    @Test
    @DisplayName("Cas n°3 : alignementRealise")
    void testAlignementRealiseDiagonaleHautDroitDescendantFalse() {
        /*---------------*//*---------------*//*---------------*/plateau[0][6] = R;
        /*---------------*//*---------------*/plateau[1][5] = R; plateau[1][6] = R;
        /*---------------*/plateau[2][4] = J; plateau[2][5] = J; plateau[2][6] = R;
        plateau[3][3] = R; plateau[3][4] = R; plateau[3][5] = J; plateau[3][6] = J;
        plateau[4][3] = J; plateau[4][4] = R; plateau[4][5] = J; plateau[4][6] = J;
        plateau[5][3] = J; plateau[5][4] = J; plateau[5][5] = R; plateau[5][6] = R;

        this.grille = new Grille(plateau);
        assertFalse(this.grille.alignementRealise(new Position(0, 6)));
    }
    @Test
    @DisplayName("Cas n°4 : alignementRealise")
    void testAlignementRealiseDiagonaleHautDroitMontantFalse() {
        /*---------------*//*---------------*//*---------------*/plateau[0][6] = R;
        /*---------------*//*---------------*/plateau[1][5] = R; plateau[1][6] = R;
        /*---------------*/plateau[2][4] = J; plateau[2][5] = J; plateau[2][6] = R;
        plateau[3][3] = R; plateau[3][4] = R; plateau[3][5] = J; plateau[3][6] = J;
        plateau[4][3] = J; plateau[4][4] = R; plateau[4][5] = J; plateau[4][6] = J;
        plateau[5][3] = J; plateau[5][4] = J; plateau[5][5] = R; plateau[5][6] = R;

        this.grille = new Grille(plateau);
        assertFalse(this.grille.alignementRealise(new Position(3, 3)));
    }
    @Test
    @DisplayName("Cas n°5 : alignementRealise")
    void testAlignementRealiseDiagonaleBasDroitMontantFalse() {
        plateau[2][3] = R; plateau[2][4] = J; plateau[2][5] = R;
        plateau[3][3] = R; plateau[3][4] = R; plateau[3][5] = J;
        plateau[4][3] = J; plateau[4][4] = R; plateau[4][5] = J;
        plateau[5][3] = J; plateau[5][4] = J; plateau[5][5] = R; plateau[5][6] = R;

        this.grille = new Grille(plateau);
        assertFalse(this.grille.alignementRealise(new Position(5, 6)));
    }
    @Test
    @DisplayName("Cas n°6 : alignementRealise")
    void testAlignementRealiseDiagonaleBasDroitDescendantFalse() {
        plateau[2][3] = R; plateau[2][4] = J; plateau[2][5] = J;
        plateau[3][3] = R; plateau[3][4] = R; plateau[3][5] = J;
        plateau[4][3] = J; plateau[4][4] = R; plateau[4][5] = J;
        plateau[5][3] = J; plateau[5][4] = J; plateau[5][5] = R; plateau[5][6] = R;

        this.grille = new Grille(plateau);
        assertFalse(this.grille.alignementRealise(new Position(2, 3)));
    }
    @Test
    @DisplayName("Cas n°7 : alignementRealise")
    void testAlignementRealiseDiagonaleBasGaucheMontantFalse() {
        /*---------------*/plateau[2][3] = J; plateau[2][4] = R; plateau[2][5] = R;
        /*---------------*/plateau[3][3] = R; plateau[3][4] = J; plateau[3][5] = J;
        /*---------------*/plateau[4][3] = R; plateau[4][4] = J; plateau[4][5] = J;
        plateau[5][3] = R; plateau[5][4] = J; plateau[5][5] = R; plateau[5][6] = R;

        this.grille = new Grille(plateau);
        assertFalse(this.grille.alignementRealise(new Position(5, 0)));
    }
    @Test
    @DisplayName("Cas n°8 : alignementRealise")
    void testAlignementRealiseDiagonaleBasGaucheDescendantFalse() {
        /*---------------*/plateau[2][3] = J; plateau[2][4] = R; plateau[2][5] = R;
        /*---------------*/plateau[3][3] = R; plateau[3][4] = J; plateau[3][5] = J;
        /*---------------*/plateau[4][3] = R; plateau[4][4] = J; plateau[4][5] = J;
        plateau[5][3] = R; plateau[5][4] = J; plateau[5][5] = R; plateau[5][6] = R;

        this.grille = new Grille(plateau);
        assertFalse(this.grille.alignementRealise(new Position(2, 3)));
    }
}

