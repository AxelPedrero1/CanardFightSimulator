public class CanardElectrique extends Canard {
    private boolean capaciteUtilisee = false;

    public CanardElectrique(String nom, int pointsDeVie, int pointsAttaque) {
        super(nom, pointsDeVie, pointsAttaque, TypeCanard.ELECTRIQUE);
    }

    /**
     * Capacite speciale : neutralise les faiblesses, c'est-à-dire
     * ses attaques s'effectuent sans multiplicateur (toujours 1.0).
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
        System.out.println(getNom() + " active sa capacite speciale : ses attaques ce tour seront executees sans multiplicateur !");
        // L'effet est intrinsèque puisque getMultiplicateur retourne 1.0 pour ELECTRIQUE.
        capaciteUtilisee = true;
    }
}
