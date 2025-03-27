public class CanardFeu extends Canard {
    private boolean capaciteUtilisee = false;

    public CanardFeu(String nom, int pointsDeVie, int pointsAttaque) {
        super(nom, pointsDeVie, pointsAttaque, TypeCanard.FEU);
    }

    /**
     * Capacité spéciale CanardFeu : inflige des dégâts supplémentaires pendant un tour.
     */
    @Override
    public void activerCapaciteSpeciale() {
        if (!capaciteUtilisee) {
            System.out.println(getNom() + " active sa capacité spéciale : Feu supplémentaire !");
            augmenterAttaque(10); // Augmente l'attaque de 10 points
            capaciteUtilisee = true;
        } else {
            System.out.println("La capacité spéciale de " + getNom() + " a déjà été utilisée.");
        }
    }

    private void augmenterAttaque(int bonus) {
        int nouvelleAttaque = getPointsAttaque() + bonus;
        System.out.println(getNom() + "  son attaque augmente de " + bonus + " pts (nvl attaque = " + nouvelleAttaque + ").");
        setPointsAttaque(nouvelleAttaque);
    }
}
