package nl.tudelft.jpacman.npc;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.sprite.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public abstract class Ghost extends Unit {

    private final Map<Direction, Sprite> sprites;
    private final int moveInterval;
    private final int intervalVariation;

    public Direction nextMove() {
        return nextAiMove().orElseGet(this::randomMove);
    }

    public abstract Optional<Direction> nextAiMove();

    protected Ghost(Map<Direction, Sprite> spriteMap, int moveInterval, int intervalVariation) {
        this.sprites = spriteMap;
        this.intervalVariation = intervalVariation;
        this.moveInterval = moveInterval;
    }

    @Override
    public Sprite getSprite() {
        return sprites.get(getDirection());
    }

    public long getInterval() {
        return this.moveInterval + new Random().nextInt(this.intervalVariation);
    }

    protected Direction randomMove() {
        Square square = getSquare();
        List<Direction> directions = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            if (square.getSquareAt(direction).isAccessibleTo(this)) {
                directions.add(direction);
            }
        }
        if (directions.isEmpty()) {
            return null;
        }
        int i = new Random().nextInt(directions.size());
        return directions.get(i);
    }
}
