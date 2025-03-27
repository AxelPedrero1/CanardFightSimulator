import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CanardTest {
    private Canard canardEau;
    private Canard canardFeu;
    private Canard canardGlace;
    private Canard canardVent;

    @Before
    public void setUp() {
        // Given : 4 canards initialisés avec 100 PV et 20 PA chacun
        canardEau = new CanardEau("CanardEau", 100, 20);
        canardFeu = new CanardFeu("CanardFeu", 100, 20);
        canardGlace = new CanardGlace("CanardGlace", 100, 20);
        canardVent = new CanardVent("CanardVent", 100, 20);
    }

    @Test
    public void testMultiplicateur() {
        // Given : des types de canard spécifiques
        // When : on calcule le multiplicateur via la méthode getMultiplicateur
        // Then : le résultat doit correspondre aux interactions attendues

        // Cas 1 : Eau > Feu doit donner un multiplicateur de 1.5
        assertEquals(1.5, TypeCanard.getMultiplicateur(TypeCanard.EAU, TypeCanard.FEU), 0.001);
        // Cas 2 : Feu > Glace doit donner un multiplicateur de 1.5
        assertEquals(1.5, TypeCanard.getMultiplicateur(TypeCanard.FEU, TypeCanard.GLACE), 0.001);
        // Cas 3 : Glace > Vent doit donner un multiplicateur de 1.5
        assertEquals(1.5, TypeCanard.getMultiplicateur(TypeCanard.GLACE, TypeCanard.VENT), 0.001);
        // Cas 4 : Vent > Eau doit donner un multiplicateur de 1.5
        assertEquals(1.5, TypeCanard.getMultiplicateur(TypeCanard.VENT, TypeCanard.EAU), 0.001);
        // Cas 5 : Feu attaquant Eau doit donner un multiplicateur de 0.5
        assertEquals(0.5, TypeCanard.getMultiplicateur(TypeCanard.FEU, TypeCanard.EAU), 0.001);
        // Cas 6 : Même type doit donner un multiplicateur de 1.0
        assertEquals(1.0, TypeCanard.getMultiplicateur(TypeCanard.EAU, TypeCanard.EAU), 0.001);
    }

    @Test
    public void testAttaquer() {
        // Given : un scénario de combat où chaque canard attaque un autre
        // When : une attaque est effectuée en tenant compte des multiplicateurs et de l'aléatoire
        // Then : la cible perd le nombre de PV attendu (70 en attaque normale ou 40 en cas de critique)

        // EX 1 : CanardEau attaque CanardFeu (Eau > Feu)
        canardEau.attaquer(canardFeu);
        int pvFeu = canardFeu.getPointsDeVie();
        assertTrue("PV de CanardFeu doit être 70 (non critique) ou 40 (critique), mais était " + pvFeu,
                pvFeu == 70 || pvFeu == 40);

        // EX 2 : CanardFeu attaque CanardGlace (Feu > Glace)
        canardFeu.attaquer(canardGlace);
        int pvGlace = canardGlace.getPointsDeVie();
        assertTrue("PV de CanardGlace doit être 70 (non critique) ou 40 (critique), mais était " + pvGlace,
                pvGlace == 70 || pvGlace == 40);

        // EX 3 : CanardGlace attaque CanardVent (Glace > Vent)
        canardGlace.attaquer(canardVent);
        int pvVent = canardVent.getPointsDeVie();
        assertTrue("PV de CanardVent doit être 70 (non critique) ou 40 (critique), mais était " + pvVent,
                pvVent == 70 || pvVent == 40);

        // EX 4 : CanardVent attaque CanardEau (Vent > Eau)
        canardVent.attaquer(canardEau);
        int pvEau = canardEau.getPointsDeVie();
        assertTrue("PV de CanardEau doit être 70 (non critique) ou 40 (critique), mais était " + pvEau,
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

        // Pour CanardEau : la capacité spéciale régénère 20 PV
        int pvInitial = canardEau.getPointsDeVie();
        canardEau.activerCapaciteSpeciale(canardEau); // L'adversaire n'est pas utilisé ici
        assertEquals("Capacité spéciale de CanardEau : PV augmentés de 20", pvInitial + 20, canardEau.getPointsDeVie());

        // Pour CanardFeu : la capacité spéciale augmente l'attaque de 10 points
        int paInitialFeu = canardFeu.getPointsAttaque();
        canardFeu.activerCapaciteSpeciale(canardFeu); // L'adversaire n'est pas utilisé ici
        assertEquals("Capacité spéciale de CanardFeu : PA augmentés de 10", paInitialFeu + 10, canardFeu.getPointsAttaque());

        // Pour CanardVent : la capacité spéciale augmente l'attaque de 5 points
        int paInitialVent = canardVent.getPointsAttaque();
        canardVent.activerCapaciteSpeciale(canardVent); // L'adversaire n'est pas utilisé ici
        assertEquals("Capacité spéciale de CanardVent : PA augmentés de 5", paInitialVent + 5, canardVent.getPointsAttaque());

        // Pour CanardGlace : la capacité spéciale gèle un adversaire pendant 2 tours
        try {
            canardGlace.activerCapaciteSpeciale(canardFeu);
        } catch (Exception e) {
            fail("L'activation de la capacité spéciale de CanardGlace ne doit pas générer d'exception.");
        }
    }
}
