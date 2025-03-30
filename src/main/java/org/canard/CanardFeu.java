package main.java.org.canard;

public class CanardFeu extends Canard {
    private boolean capaciteUtilisee = false;

    public CanardFeu(String nom, int pointsDeVie, int pointsAttaque) {
        super(nom, pointsDeVie, pointsAttaque, TypeCanard.FEU);
    }

    /**
     * Capacite speciale : augmente l'attaque de 10 points.
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
        System.out.println(getNom() + " active sa capacite speciale : augmentation de l'attaque de 10 points !");
        augmenterAttaque(10); // Augmente l'attaque de 10 points
        capaciteUtilisee = true;
    }

    private void augmenterAttaque(int bonus) {
        int nouvelleAttaque = getPointsAttaque() + bonus;
        System.out.println(getNom() + " voit son attaque augmenter de " + bonus + " points (nouvelle attaque = " + nouvelleAttaque + ").");
        setPointsAttaque(nouvelleAttaque);
    }
}

