public abstract class Canard {
    private String nom;
    private int pointsDeVie;
    private int pointsAttaque;
    private TypeCanard type;

    public Canard(String nom, int pointsDeVie, int pointsAttaque, TypeCanard type) {
        this.nom = nom;
        this.pointsDeVie = pointsDeVie;
        this.pointsAttaque = pointsAttaque;
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public int getPointsDeVie() {
        return pointsDeVie;
    }

    public int getPointsAttaque() {
        return pointsAttaque;
    }

    public TypeCanard getType() {
        return type;
    }


    /**
     * Effectue une attaque sur un autre canard en appliquant le multiplicateur de dégâts
     */
    public void attaquer(Canard autreCanard) {
        double multiplicateur = TypeCanard.getMultiplicateur(this.type, autreCanard.getType());
        int degats = (int) (this.pointsAttaque * multiplicateur);
        System.out.println(this.nom + " attaque " + autreCanard.getNom() + " et inflige " + degats + " dégâts.");
        autreCanard.subirDegats(degats);
    }

    /**
     * Réduit les points de vie du canard en fonction des dégâts subis.
     */
    public void subirDegats(int degats) {
        pointsDeVie -= degats;
        if (pointsDeVie < 0) {
            pointsDeVie = 0;
        }
        System.out.println(nom + " a maintenant " + pointsDeVie + " PV.");
    }

    public boolean estKO() {
        return pointsDeVie <= 0;
    }



}
