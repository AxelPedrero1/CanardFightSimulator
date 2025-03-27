import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class Canard {
    private String nom;
    private int pointsDeVie;
    private int pointsAttaque;
    private TypeCanard type;
    protected int pointsEnergie;
    protected List<StatusEffect> statusEffects; // Liste des effets de statut
    private Random random;


    public Canard(String nom, int pointsDeVie, int pointsAttaque, TypeCanard type) {
        this.nom = nom;
        this.pointsDeVie = pointsDeVie;
        this.pointsAttaque = pointsAttaque;
        this.type = type;
        this.pointsEnergie = 100; // Initialisation à 100 PE
        this.statusEffects = new ArrayList<>();
        this.random = new Random();
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

    public int getPointsEnergie() {
        return pointsEnergie;
    }


    /**
     * Applique les effets de statut actifs sur le canard.
     * - BRULE : le canard perd 5 PV à chaque tour.
     * - GELE : le canard ne peut pas agir ce tour.
     * - PARALYSE : le canard a 50% de chance de ne pas agir.
     */
    public void appliquerEffets() {
        Iterator<StatusEffect> it = statusEffects.iterator();
        while (it.hasNext()) {
            StatusEffect effet = it.next();
            switch (effet.getType()) {
                case BRULE:
                    System.out.println(nom + " est brule et perd 5 PV.");
                    this.subirDegats(5);
                    break;
                case GELE:
                    System.out.println(nom + " est gele et ne peut pas agir ce tour.");
                    break;
                case PARALYSE:
                    System.out.println(nom + " est paralyse, 50% de chance de ne pas agir.");
                    break;
            }
            effet.decrementTurn();
            if (effet.isExpired()) {
                System.out.println(nom + " n'est plus affecte par " + effet.getType());
                it.remove();
            }
        }
    }

    /**
     * Verifie si le canard peut agir ce tour en fonction des effets de statut.
     * Pour GELE, il ne peut jamais agir.
     * Pour PARALYSE, il a 50% de chance de ne pas agir.
     */
    public boolean peutAgir() {
        for (StatusEffect effet : statusEffects) {
            if (effet.getType() == StatusEffect.Type.GELE) {
                return false;
            }
            if (effet.getType() == StatusEffect.Type.PARALYSE) {
                if (random.nextDouble() < 0.5) {
                    return false;
                }
            }
        }
        return true;
    }



    /**
     * Attaque un autre canard en appliquant le multiplicateur de degats.
     * L'attaque consomme 5 PE.
     * Il y a 10% de chance d'une attaque critique qui double les degats.
     */
    public void attaquer(Canard autreCanard) {
        if (pointsEnergie < 5) {
            System.out.println(nom + " n'a pas assez d'energie pour attaquer.");
            return;
        }
        if (!peutAgir()) {
            System.out.println(nom + " ne peut pas agir ce tour a cause de ses effets.");
            return;
        }
        // Consommation d'énergie
        pointsEnergie -= 5;

        double multiplicateur = TypeCanard.getMultiplicateur(this.type, autreCanard.getType());
        int degats = (int) (this.pointsAttaque * multiplicateur);
        // Verification d'une attaque critique
        if (random.nextDouble() < 0.1) {
            degats *= 2;
            System.out.println("Attaque critique de " + nom + "!");
        }
        System.out.println(nom + " attaque " + autreCanard.getNom() + " et inflige " + degats + " degats.");
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

    /**
     * Permet à un canard dactiver sa capacite
     * Dans certains cas, comme pour CanardGlace, il faut pouvoir cibler un adversaire.
     */
    public abstract void activerCapaciteSpeciale(Canard adversaire);

    // Méthodes protégées pour modifier certains attributs, utiles pour les capacités spéciales
    protected void augmenterPV(int points) {
        this.pointsDeVie += points;
        System.out.println(nom + " regagne " + points + " PV et a maintenant " + pointsDeVie + " PV.");
    }

    protected void setPointsAttaque(int pa) {
        this.pointsAttaque = pa;
    }

    /**
     * Ajoute un effet de statut sur le canard pour une duree en tours.
     */
    public void ajouterStatusEffect(StatusEffect.Type type, int duree) {
        statusEffects.add(new StatusEffect(type, duree));
        System.out.println(nom + " est affecte par " + type + " pendant " + duree + " tours.");
    }

}
