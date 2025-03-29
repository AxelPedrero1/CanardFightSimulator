public class Potion extends Item {
    private int restorePV;
    private int restorePE;

    public Potion(String nom, int restorePV, int restorePE) {
        super(nom);
        this.restorePV = restorePV;
        this.restorePE = restorePE;
    }

    @Override
    public void utiliser(Canard canard) {
        canard.augmenterPV(restorePV);
        canard.augmenterEnergie(restorePE);
        System.out.println(canard.getNom() + " utilise " + getNom() +
                " et regagne " + restorePV + " PV et " + restorePE + " PE.");
    }
}
