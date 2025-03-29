public class CanardToxique extends Canard {
    private boolean capaciteUtilisee = false;

    public CanardToxique(String nom, int pointsDeVie, int pointsAttaque) {
        super(nom, pointsDeVie, pointsAttaque, TypeCanard.TOXIQUE);
    }

    /**
     * Capacite speciale : empoisonne l'adversaire, qui perd 5 PV par tour pendant 3 tours.
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
        System.out.println(getNom() + " active sa capacite speciale : il empoisonne " + adversaire.getNom() + " pendant 3 tours !");
        adversaire.ajouterStatusEffect(StatusEffect.Type.POISON, 3);
        capaciteUtilisee = true;
    }
}
