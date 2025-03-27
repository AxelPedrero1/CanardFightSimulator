public class CanardEau extends Canard {
    private boolean capaciteUtilisee = false;

    public CanardEau(String nom, int pointsDeVie, int pointsAttaque) {
        super(nom, pointsDeVie, pointsAttaque, TypeCanard.EAU);
    }

    /**
     * Capacité spéciale : régénère 20 PV
     */
    @Override
    public void activerCapaciteSpeciale() {
        if (!capaciteUtilisee) {
            System.out.println(getNom() + " active sa capacité spéciale : Régénération de 20 PV !");
            augmenterPV(20);
            capaciteUtilisee = true;
        } else {
            System.out.println("La capacité spéciale de " + getNom() + " a déjà été utilisée.");
        }
    }
}
