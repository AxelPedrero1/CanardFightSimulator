public class StatusEffect {
    public enum Type {
        BRULE, GELE, PARALYSE ,POISON
    }

    private Type type;
    private int remainingTurns;

    public StatusEffect(Type type, int remainingTurns) {
        this.type = type;
        this.remainingTurns = remainingTurns;
    }

    public Type getType() {
        return type;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public void decrementTurn() {
        remainingTurns--;
    }

    public boolean isExpired() {
        return remainingTurns <= 0;
    }
}
