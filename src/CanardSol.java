public class CanardSol extends Canard {
    private boolean capaciteUtilisee = false;

    public CanardSol(String nom, int pointsDeVie, int pointsAttaque) {
        super(nom, pointsDeVie, pointsAttaque, TypeCanard.SOL);
    }

    /**
     * Capacite speciale : augmente la defense en augmentant les PV de 10.
     * Coute 15 PE.
     */
    @Override
    public void activerCapaciteSpeciale(Canard adversaire) {
        if (capaciteUtilisee) {
            System.out.println(getNom() + " a deja utilise sa capacite speciale.");
            return;
        }
        if (getPointsEnergie() < 15) {
            System.out.println(getNom() + " n'a pas assez d'energie pour activer sa capacite speciale.");
            return;
        }
        pointsEnergie -= 15;
        System.out.println(getNom() + " active sa capacite speciale : augmentation de PV de 10 !");
        augmenterPV(10);
        capaciteUtilisee = true;
    }
}
