package main.java.org.canard;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue dans main.java.org.canard.Canard Fighter Simulator !");

        boolean quitter = false;
        Canard canard1 = null;
        Canard canard2 = null;

        while (!quitter) {
            System.out.println("\nMenu :");
            System.out.println("1. Créer des canards");
            System.out.println("2. Lancer une bataille");
            System.out.println("3. Quitter");
            System.out.print("Votre choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consomme la nouvelle ligne

            switch (choix) {
                case 1:
                    canard1 = creerCanard(scanner, 1);
                    canard2 = creerCanard(scanner, 2);
                    break;
                case 2:
                    if (canard1 == null || canard2 == null) {
                        System.out.println("Veuillez d'abord créer les canards !");
                    } else {
                        simulerBataille(canard1, canard2, scanner);
                    }
                    break;
                case 3:
                    quitter = true;
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
                    break;
            }
        }
        System.out.println("Au revoir !");
        scanner.close();
    }

    public static Canard creerCanard(Scanner scanner, int numero) {
        System.out.println("\nCréation du canard " + numero);
        System.out.print("Entrez le nom du canard : ");
        String nom = scanner.nextLine();
        System.out.print("Choisissez le type (Eau, Feu, Glace, Vent, Electrique, Toxique, Sol) : ");
        String typeStr = scanner.nextLine().toUpperCase();
        TypeCanard type = TypeCanard.valueOf(typeStr);

        int pv;
        do {
            System.out.print("Entrez les points de vie (entre 50 et 200) : ");
            pv = scanner.nextInt();
        } while (pv < 50 || pv > 200);

        int pa;
        do {
            System.out.print("Entrez les points d'attaque (entre 10 et 50) : ");
            pa = scanner.nextInt();
        } while (pa < 10 || pa > 50);

        scanner.nextLine(); // Consomme la nouvelle ligne

        switch (type) {
            case EAU:
                return new CanardEau(nom, pv, pa);
            case FEU:
                return new CanardFeu(nom, pv, pa);
            case GLACE:
                return new CanardGlace(nom, pv, pa);
            case VENT:
                return new CanardVent(nom, pv, pa);
            case ELECTRIQUE:
                return new CanardElectrique(nom, pv, pa);
            case TOXIQUE:
                return new CanardToxique(nom, pv, pa);
            case SOL:
                return new CanardSol(nom, pv, pa);
            default:
                return null;
        }
    }

    public static void simulerBataille(Canard c1, Canard c2, Scanner scanner) {
        System.out.println("\nDébut de la bataille entre " + c1.getNom() + " et " + c2.getNom());
        int tour = 1;
        // Création d'une potion à utiliser pendant le combat
        Potion potion = new Potion("main.java.org.canard.Potion de Soin", 20, 20);
        while (!c1.estKO() && !c2.estKO()) {
            System.out.println("\nTour " + tour);

            // Applique les effets de statut sur chaque canard au début du tour
            c1.appliquerEffets();
            c2.appliquerEffets();

            // Si un canard est KO après les effets, on arrête la bataille
            if (c1.estKO() || c2.estKO()) break;

            // Possibilité d'utiliser une potion
            System.out.print("Voulez-vous utiliser une potion sur " + c1.getNom() + " ? (oui/non) : ");
            String rep = scanner.nextLine();
            if (rep.equalsIgnoreCase("oui")) {
                potion.utiliser(c1);
            }

            System.out.print("Voulez-vous utiliser une potion sur " + c2.getNom() + " ? (oui/non) : ");
            rep = scanner.nextLine();
            if (rep.equalsIgnoreCase("oui")) {
                potion.utiliser(c2);
            }

            // c1 attaque c2
            c1.attaquer(c2);
            if (c2.estKO()) {
                System.out.println(c2.getNom() + " est KO !");
                break;
            }
            // c2 attaque c1
            c2.attaquer(c1);
            if (c1.estKO()) {
                System.out.println(c1.getNom() + " est KO !");
                break;
            }

            // Propose aux joueurs d'activer leur capacité spéciale
            System.out.print("Activer la capacité spéciale de " + c1.getNom() + " ? (oui/non) : ");
            String rep1 = scanner.nextLine();
            if (rep1.equalsIgnoreCase("oui")) {
                c1.activerCapaciteSpeciale(c2);

            }

            System.out.print("Activer la capacité spéciale de " + c2.getNom() + " ? (oui/non) : ");
            String rep2 = scanner.nextLine();
            if (rep2.equalsIgnoreCase("oui")) {
                c2.activerCapaciteSpeciale(c1);
            }

            tour++;
        }
        System.out.println("Bataille terminée !");

        // Evolution : le gagnant évolue
        if (c1.estKO() && !c2.estKO()) {
            System.out.println(c2.getNom() + " a gagné !");
            c2.evoluer();
        } else if (c2.estKO() && !c1.estKO()) {
            System.out.println(c1.getNom() + " a gagné !");
            c1.evoluer();
        } else {
            System.out.println("Match nul !");
        }
    }
}
