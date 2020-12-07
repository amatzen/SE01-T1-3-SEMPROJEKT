package dk.sdu.mmmi.swe20.t1.g3.Config;

public enum Direction {
    UP("op"),
    DOWN("ned"),
    LEFT("venstre"),
    RIGHT("højre");

    private String directionString;
    Direction(String directionString) {
        this.directionString = directionString;
    }

    public String getDirectionString() { return directionString; }
}
