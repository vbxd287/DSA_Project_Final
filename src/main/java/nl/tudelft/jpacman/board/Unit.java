package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.sprite.Sprite;

public abstract class Unit {

    private Square square;
    private Direction direction;

    protected Unit() {
        this.direction = Direction.EAST;
    }

    public void setDirection(Direction newDirection) {
        this.direction = newDirection;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public Square getSquare() {
        assert invariant();
        assert square != null;
        return square;
    }

    public boolean hasSquare() {
        return square != null;
    }

    public void occupy(Square target) {
        assert target != null;

        if (square != null) {
            square.remove(this);
        }
        square = target;
        target.put(this);
        assert invariant();
    }

    public void leaveSquare() {
        if (square != null) {
            square.remove(this);
            square = null;
        }
        assert invariant();
    }

    protected boolean invariant() {
        return square == null || square.getOccupants().contains(this);
    }

    public abstract Sprite getSprite();

    public Square squaresAheadOf(int amountToLookAhead) {
        Direction targetDirection = this.getDirection();
        Square destination = this.getSquare();
        for (int i = 0; i < amountToLookAhead; i++) {
            destination = destination.getSquareAt(targetDirection);
        }
        return destination;
    }
}
