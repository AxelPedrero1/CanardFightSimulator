public class CanardVent extends Canard {
    private boolean capaciteUtilisee = false;

    public CanardVent(String nom, int pointsDeVie, int pointsAttaque) {
        super(nom, pointsDeVie, pointsAttaque, TypeCanard.VENT);
    }

    /**
     * Capacité spéciale CanardVent : augmente temporairement sa vitesse d’attaque (2x attaque,
     * 3x attaque, etc.).
     */
    @Override
    public void activerCapaciteSpeciale() {
        if (!capaciteUtilisee) {
            System.out.println(getNom() + " active sa capacité spéciale : Augmentation vitesse d'attaque !");
            augmenterAttaque(5); // Augmente l'attaque de 5 points
            capaciteUtilisee = true;
        } else {
            System.out.println("La capacité spéciale de " + getNom() + " a déjà été utilisée.");
        }
    }

    private void augmenterAttaque(int bonus) {
        int nouvelleAttaque = getPointsAttaque() + bonus;
        System.out.println(getNom() + " voit son attaque augmenter de " + bonus + " points (nouvelle attaque = " + nouvelleAttaque + ").");
        setPointsAttaque(nouvelleAttaque);
    }
}
