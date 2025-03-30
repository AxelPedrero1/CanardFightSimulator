package main.java.org.canard;

public abstract class Item {
    private String nom;

    public Item(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public abstract void utiliser(Canard canard);
}
