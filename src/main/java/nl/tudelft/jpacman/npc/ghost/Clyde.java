package nl.tudelft.jpacman.npc.ghost;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.sprite.Sprite;

public class Clyde extends Ghost {

    private static final int SHYNESS = 8;
    private static final int INTERVAL_VARIATION = 50;
    private static final int MOVE_INTERVAL = 250;

    private static final Map<Direction, Direction> OPPOSITES = new EnumMap<>(Direction.class);

    static {
        OPPOSITES.put(Direction.NORTH, Direction.SOUTH);
        OPPOSITES.put(Direction.SOUTH, Direction.NORTH);
        OPPOSITES.put(Direction.WEST, Direction.EAST);
        OPPOSITES.put(Direction.EAST, Direction.WEST);
    }

    public Clyde(Map<Direction, Sprite> spriteMap) {
        super(spriteMap, MOVE_INTERVAL, INTERVAL_VARIATION);
    }

    @Override
    public Optional<Direction> nextAiMove() {
        assert hasSquare();

        Unit nearest = Navigation.findNearest(Player.class, getSquare());
        if (nearest == null) {
            return Optional.empty();
        }
        assert nearest.hasSquare();
        Square target = nearest.getSquare();

        List<Direction> path = Navigation.shortestPath(getSquare(), target, this);
        if (path != null && !path.isEmpty()) {
            Direction direction = path.get(0);
            if (path.size() <= SHYNESS) {
                return Optional.ofNullable(OPPOSITES.get(direction));
            }
            return Optional.of(direction);
        }
        return Optional.empty();
    }
}
