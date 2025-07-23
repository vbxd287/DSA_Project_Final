package nl.tudelft.jpacman.npc.ghost;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.sprite.Sprite;

public class Pinky extends Ghost {

    private static final int SQUARES_AHEAD = 4;
    private static final int INTERVAL_VARIATION = 50;
    private static final int MOVE_INTERVAL = 200;

    public Pinky(Map<Direction, Sprite> spriteMap) {
        super(spriteMap, MOVE_INTERVAL, INTERVAL_VARIATION);
    }

    @Override
    public Optional<Direction> nextAiMove() {
        assert hasSquare();

        Unit player = Navigation.findNearest(Player.class, getSquare());
        if (player == null) {
            return Optional.empty();
        }
        assert player.hasSquare();
        Square destination = player.squaresAheadOf(SQUARES_AHEAD);

        List<Direction> path = Navigation.shortestPath(getSquare(), destination, this);
        if (path != null && !path.isEmpty()) {
            return Optional.ofNullable(path.get(0));
        }
        return Optional.empty();
    }
}
