package nl.tudelft.jpacman.board;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import nl.tudelft.jpacman.sprite.Sprite;

public abstract class Square {

    private final List<Unit> occupants;
    private final Map<Direction, Square> neighbours;

    protected Square() {
        this.occupants = new ArrayList<>();
        this.neighbours = new EnumMap<>(Direction.class);
        assert invariant();
    }

    public Square getSquareAt(Direction direction) {
        return neighbours.get(direction);
    }

    public void link(Square neighbour, Direction direction) {
        neighbours.put(direction, neighbour);
        assert invariant();
    }

    public List<Unit> getOccupants() {
        return ImmutableList.copyOf(occupants);
    }

    void put(Unit occupant) {
        assert occupant != null;
        assert !occupants.contains(occupant);
        occupants.add(occupant);
    }

    void remove(Unit occupant) {
        assert occupant != null;
        occupants.remove(occupant);
    }

    protected final boolean invariant(Square this) {
        for (Unit occupant : occupants) {
            if (occupant.hasSquare() && occupant.getSquare() != this) {
                return false;
            }
        }
        return true;
    }

    public abstract boolean isAccessibleTo(Unit unit);

    public abstract Sprite getSprite();
}
