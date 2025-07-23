package nl.tudelft.jpacman.level;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostColor;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.sprite.Sprite;

public class LevelFactory {

    private static final int GHOSTS = 4;
    private static final int BLINKY = 0;
    private static final int INKY = 1;
    private static final int PINKY = 2;
    private static final int CLYDE = 3;

    private static final int PELLET_VALUE = 10;

    private final PacManSprites sprites;
    private int ghostIndex;
    private final GhostFactory ghostFact;

    public LevelFactory(PacManSprites spriteStore, GhostFactory ghostFactory) {
        this.sprites = spriteStore;
        this.ghostIndex = -1;
        this.ghostFact = ghostFactory;
    }

    public Level createLevel(Board board, List<Ghost> ghosts,
                             List<Square> startPositions) {
        CollisionMap collisionMap = new PlayerCollisions();
        return new Level(board, ghosts, startPositions, collisionMap);
    }

    Ghost createGhost() {
        ghostIndex++;
        ghostIndex %= GHOSTS;
        switch (ghostIndex) {
            case BLINKY:
                return ghostFact.createBlinky();
            case INKY:
                return ghostFact.createInky();
            case PINKY:
                return ghostFact.createPinky();
            case CLYDE:
                return ghostFact.createClyde();
            default:
                return new RandomGhost(sprites.getGhostSprite(GhostColor.RED));
        }
    }

    public Pellet createPellet() {
        return new Pellet(PELLET_VALUE, sprites.getPelletSprite());
    }

    private static final class RandomGhost extends Ghost {

        private static final long DELAY = 175L;

        RandomGhost(Map<Direction, Sprite> ghostSprite) {
            super(ghostSprite, (int) DELAY, 0);
        }

        @Override
        public Optional<Direction> nextAiMove() {
            return Optional.empty();
        }
    }
}
