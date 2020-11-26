package dk.sdu.mmmi.swe20.t1.g3.Models;

public enum Direction {
    UP("op"),
    DOWN("ned"),
    LEFT("venstre"),
    RIGHT("højre");

    private String dirText;
    Direction(String dirText) { this.dirText = dirText; }

    @Override
    public String toString() { return dirText; }
}
