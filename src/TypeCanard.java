public enum TypeCanard {
    EAU, FEU, GLACE, VENT;

    /**
     * Retourne le multiplicateur de dégâts en fonction des types.
     * Eau > Feu, Feu > Glace, Glace > Vent, Vent > Eau : 1.5 (fort)
     * Inversement : 0.5 (faible), sinon 1.0.
     */
    public static double getMultiplicateur(TypeCanard attaquant, TypeCanard cible) {
        if ((attaquant == EAU && cible == FEU) ||
                (attaquant == FEU && cible == GLACE) ||
                (attaquant == GLACE && cible == VENT) ||
                (attaquant == VENT && cible == EAU)) {
            return 1.5;
        } else if ((attaquant == FEU && cible == EAU) ||
                (attaquant == GLACE && cible == FEU) ||
                (attaquant == VENT && cible == GLACE) ||
                (attaquant == EAU && cible == VENT)) {
            return 0.5;
        }
        return 1.0;
    }
}
