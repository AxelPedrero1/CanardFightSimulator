public class CanardEau extends Canard {
    private boolean capaciteUtilisee = false;

    public CanardEau(String nom, int pointsDeVie, int pointsAttaque) {
        super(nom, pointsDeVie, pointsAttaque, TypeCanard.EAU);
    }

    /**
     * Capacite speciale : regenere 20 PV.
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
        System.out.println(getNom() + " active sa capacité spéciale : REegen 20 PV !");
        augmenterPV(20);
        capaciteUtilisee = true;
    }
}



