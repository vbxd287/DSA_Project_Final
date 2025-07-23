package nl.tudelft.jpacman.board;

public enum Direction {

    NORTH(0, -1),
    SOUTH(0, 1),
    WEST(-1, 0),
    EAST(1, 0);

    private final int deltaX;
    private final int deltaY;

    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }
}
