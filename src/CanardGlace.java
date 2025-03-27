public class CanardGlace extends Canard {
    private boolean capaciteUtilisee = false;

    public CanardGlace(String nom, int pointsDeVie, int pointsAttaque) {
        super(nom, pointsDeVie, pointsAttaque, TypeCanard.GLACE);
    }

    /**
     * Capacite speciale : gèle l'adversaire, l'empêchant d'agir pendant 2 tours.
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
        System.out.println(getNom() + " active sa capacite speciale : il glace " + adversaire.getNom() + " pour 2 tours !");
        adversaire.ajouterStatusEffect(StatusEffect.Type.GELE, 2);
        capaciteUtilisee = true;
    }
}
