package test.java.org.canards;

import static org.junit.Assert.*;

import main.java.org.canard.*;
import org.junit.Before;
import org.junit.Test;

public class CanardTest {
    private Canard canardEau;
    private Canard canardFeu;
    private Canard canardGlace;
    private Canard canardVent;
    private Canard canardElectrique;
    private Canard canardToxique;
    private Canard canardSol;

    @Before
    public void setUp() {
        // Given : 7 canards initialisés avec 100 PV et 20 PA chacun
        canardEau = new CanardEau("main.java.org.canard.CanardEau", 100, 20);
        canardFeu = new CanardFeu("main.java.org.canard.CanardFeu", 100, 20);
        canardGlace = new CanardGlace("main.java.org.canard.CanardGlace", 100, 20);
        canardVent = new CanardVent("main.java.org.canard.CanardVent", 100, 20);
        canardElectrique = new CanardElectrique("main.java.org.canard.CanardElectrique", 100, 20);
        canardToxique = new CanardToxique("main.java.org.canard.CanardToxique", 100, 20);
        canardSol = new CanardSol("main.java.org.canard.CanardSol", 100, 20);
    }

    @Test
    public void testMultiplicateur() {
        // Given : des types de canard spécifiques
        // When : on calcule le multiplicateur via la méthode getMultiplicateur
        // Then : le résultat doit correspondre aux interactions attendues

        // Cas pour les types de base
        assertEquals(1.5, TypeCanard.getMultiplicateur(TypeCanard.EAU, TypeCanard.FEU), 0.001);
        assertEquals(1.5, TypeCanard.getMultiplicateur(TypeCanard.FEU, TypeCanard.GLACE), 0.001);
        assertEquals(1.5, TypeCanard.getMultiplicateur(TypeCanard.GLACE, TypeCanard.VENT), 0.001);
        assertEquals(1.5, TypeCanard.getMultiplicateur(TypeCanard.VENT, TypeCanard.EAU), 0.001);
        assertEquals(0.5, TypeCanard.getMultiplicateur(TypeCanard.FEU, TypeCanard.EAU), 0.001);
        assertEquals(1.0, TypeCanard.getMultiplicateur(TypeCanard.EAU, TypeCanard.EAU), 0.001);

        // Nouveaux types
        // Cas : main.java.org.canard.CanardElectrique attaque n'importe quel type doit donner 1.0
        assertEquals(1.0, TypeCanard.getMultiplicateur(TypeCanard.ELECTRIQUE, TypeCanard.FEU), 0.001);
        assertEquals(1.0, TypeCanard.getMultiplicateur(TypeCanard.ELECTRIQUE, TypeCanard.TOXIQUE), 0.001);

        // Cas : Attaquant Eau contre cible Sol doit donner 1.5
        assertEquals(1.5, TypeCanard.getMultiplicateur(TypeCanard.EAU, TypeCanard.SOL), 0.001);
        // Cas : Attaquant Feu contre cible Sol doit donner 0.5
        assertEquals(0.5, TypeCanard.getMultiplicateur(TypeCanard.FEU, TypeCanard.SOL), 0.001);
    }

    @Test
    public void testAttaquer() {
        // Given : un scénario de combat où chaque canard attaque un autre
        // When : une attaque est effectuée en tenant compte des multiplicateurs et de l'aléatoire
        // Then : la cible perd le nombre de PV attendu (70 en attaque normale ou 40 en cas de critique)

        // EX 1 : main.java.org.canard.CanardEau attaque main.java.org.canard.CanardFeu (Eau > Feu)
        canardEau.attaquer(canardFeu);
        int pvFeu = canardFeu.getPointsDeVie();
        assertTrue("PV de main.java.org.canard.CanardFeu doit être 70 (non critique) ou 40 (critique), mais était " + pvFeu,
                pvFeu == 70 || pvFeu == 40);

        // EX 2 : main.java.org.canard.CanardFeu attaque main.java.org.canard.CanardGlace (Feu > Glace)
        canardFeu.attaquer(canardGlace);
        int pvGlace = canardGlace.getPointsDeVie();
        assertTrue("PV de main.java.org.canard.CanardGlace doit être 70 (non critique) ou 40 (critique), mais était " + pvGlace,
                pvGlace == 70 || pvGlace == 40);

        // EX 3 : main.java.org.canard.CanardGlace attaque main.java.org.canard.CanardVent (Glace > Vent)
        canardGlace.attaquer(canardVent);
        int pvVent = canardVent.getPointsDeVie();
        assertTrue("PV de main.java.org.canard.CanardVent doit être 70 (non critique) ou 40 (critique), mais était " + pvVent,
                pvVent == 70 || pvVent == 40);

        // EX 4 : main.java.org.canard.CanardVent attaque main.java.org.canard.CanardEau (Vent > Eau)
        canardVent.attaquer(canardEau);
        int pvEau = canardEau.getPointsDeVie();
        assertTrue("PV de main.java.org.canard.CanardEau doit être 70 (non critique) ou 40 (critique), mais était " + pvEau,
                pvEau == 70 || pvEau == 40);
    }

    @Test
    public void testSubirDegatsEtEstKO() {
        // Given : un canard avec 100 PV
        // When : on lui inflige 50 dégâts puis 60 dégâts supplémentaires
        // Then : après 50 dégâts il ne doit pas être KO et après 110 dégâts au total, il doit être KO

        canardEau.subirDegats(50);
        assertFalse("Le canard ne devrait pas être ko après 50 dégâts sur 100 PV", canardEau.estKO());
        canardEau.subirDegats(60);
        assertTrue("Le canard devrait être ko après un total de 110 dégâts sur 100 PV", canardEau.estKO());
    }

    @Test
    public void testCapaciteSpeciale() {
        // Given : des canards de différents types avec des capacités spéciales distinctes
        // When : on active la capacité spéciale de chaque canard avec un adversaire
        // Then : l'effet propre à chaque capacité doit être vérifié

        // Pour main.java.org.canard.CanardEau : la capacité spéciale régénère 20 PV
        int pvInitial = canardEau.getPointsDeVie();
        canardEau.activerCapaciteSpeciale(canardEau); // L'adversaire n'est pas utilisé ici
        assertEquals("Capacité spéciale de main.java.org.canard.CanardEau : PV augmentés de 20", pvInitial + 20, canardEau.getPointsDeVie());

        // Pour main.java.org.canard.CanardFeu : la capacité spéciale augmente l'attaque de 10 points
        int paInitialFeu = canardFeu.getPointsAttaque();
        canardFeu.activerCapaciteSpeciale(canardFeu); // L'adversaire n'est pas utilisé ici
        assertEquals("Capacité spéciale de main.java.org.canard.CanardFeu : PA augmentés de 10", paInitialFeu + 10, canardFeu.getPointsAttaque());

        // Pour main.java.org.canard.CanardVent : la capacité spéciale augmente l'attaque de 5 points
        int paInitialVent = canardVent.getPointsAttaque();
        canardVent.activerCapaciteSpeciale(canardVent); // L'adversaire n'est pas utilisé ici
        assertEquals("Capacité spéciale de main.java.org.canard.CanardVent : PA augmentés de 5", paInitialVent + 5, canardVent.getPointsAttaque());

        // Pour main.java.org.canard.CanardGlace : la capacité spéciale gèle un adversaire pendant 2 tours
        try {
            canardGlace.activerCapaciteSpeciale(canardFeu);
        } catch (Exception e) {
            fail("L'activation de la capacité spéciale de main.java.org.canard.CanardGlace ne doit pas générer d'exception.");
        }

        // Pour main.java.org.canard.CanardElectrique : vérifier que l'énergie diminue de 15 PE
        int peInitialElectrique = canardElectrique.getPointsEnergie();
        canardElectrique.activerCapaciteSpeciale(canardElectrique);
        assertEquals("Capacité spéciale de main.java.org.canard.CanardElectrique : PE diminués de 15", peInitialElectrique - 15, canardElectrique.getPointsEnergie());

        // Pour main.java.org.canard.CanardToxique : vérifier que l'adversaire est empoisonné (perte de 5 PV par tour)
        int pvInitialAdversaire = canardFeu.getPointsDeVie();
        canardToxique.activerCapaciteSpeciale(canardFeu);
        canardFeu.appliquerEffets(); // Application du POISON
        assertEquals("Après poison, PV de l'adversaire doivent être réduits de 5", pvInitialAdversaire - 5, canardFeu.getPointsDeVie());

        // Pour main.java.org.canard.CanardSol : la capacité spéciale augmente les PV de 10
        int pvInitialSol = canardSol.getPointsDeVie();
        canardSol.activerCapaciteSpeciale(canardSol);
        assertEquals("Capacité spéciale de main.java.org.canard.CanardSol : PV augmentés de 10", pvInitialSol + 10, canardSol.getPointsDeVie());
    }

    @Test
    public void testStatusEffects() {
        // Given : un canard avec 100 PV
        // When : on ajoute un effet BRULE et on applique les effets
        // Then : le canard perd 5 PV pour l'effet BRULE

        int pvInitial = canardEau.getPointsDeVie();
        canardEau.ajouterStatusEffect(StatusEffect.Type.BRULE, 1);
        canardEau.appliquerEffets();
        assertEquals("Après effet BRULE, PV doivent être réduits de 5", pvInitial - 5, canardEau.getPointsDeVie());

        // Given : un canard auquel on ajoute un effet GELE pour 2 tours
        // When : on vérifie qu'il ne peut pas agir pendant l'effet GELE
        canardFeu.ajouterStatusEffect(StatusEffect.Type.GELE, 2);
        assertFalse("Avec effet GELE, le canard ne doit pas pouvoir agir", canardFeu.peutAgir());
        // When : on applique les effets une première fois (1er tour)
        canardFeu.appliquerEffets();
        assertFalse("Après 1 tour, effet GELE toujours actif", canardFeu.peutAgir());
        // When : on applique les effets une seconde fois (l'effet expire)
        canardFeu.appliquerEffets();
        assertTrue("Après expiration de l'effet GELE, le canard doit pouvoir agir", canardFeu.peutAgir());
    }

    // Tests pour la personnalisation et la progression

    @Test
    public void testEvolution() {
        // Given : un canard avec 100 PV et 20 PA
        int pvInitial = canardFeu.getPointsDeVie();
        int paInitial = canardFeu.getPointsAttaque();
        // When : le canard évolue après une victoire
        canardFeu.evoluer();
        // Then : ses PV augmentent de 10 et son PA augmentent de 5
        assertEquals("Après évolution, les PV doivent augmenter de 10", pvInitial + 10, canardFeu.getPointsDeVie());
        assertEquals("Après évolution, le PA doit augmenter de 5", paInitial + 5, canardFeu.getPointsAttaque());
    }

    @Test
    public void testPotionUtilisation() {
        // Given : un canard avec 100 PV et 100 PE
        int pvInitial = canardEau.getPointsDeVie();
        int peInitial = canardEau.getPointsEnergie();
        // And une potion qui restaure 20 PV et 20 PE
        Potion potion = new Potion("main.java.org.canard.Potion de Soin", 20, 20);
        // When : on utilise la potion sur le canard
        potion.utiliser(canardEau);
        // Then : le canard gagne 20 PV et 20 PE
        assertEquals("Après utilisation de la potion, les PV doivent augmenter de 20", pvInitial + 20, canardEau.getPointsDeVie());
        assertEquals("Après utilisation de la potion, les PE doivent augmenter de 20", peInitial + 20, canardEau.getPointsEnergie());
    }
}
