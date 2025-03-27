public class CanardGlace extends Canard {
    private boolean capaciteUtilisee = false;

    public CanardGlace(String nom, int pointsDeVie, int pointsAttaque) {
        super(nom, pointsDeVie, pointsAttaque, TypeCanard.GLACE);
    }

    /**
     * Capacité spéciale CanardGlace : gèle un adversaire, lui faisant perdre un tour.
     */
    @Override
    public void activerCapaciteSpeciale() {
        if (!capaciteUtilisee) {
            System.out.println(getNom() + " active sa capacité spéciale : Gèl !");
            // À compléter : gestion d'un statut "gelé" dans la logique de combat
            capaciteUtilisee = true;
        } else {
            System.out.println("La capacité spéciale de " + getNom() + " a déjà été utilisée.");
        }
    }
}
