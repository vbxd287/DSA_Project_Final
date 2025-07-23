package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.sprite.Sprite;

public class BoardFactory {

    private final PacManSprites sprites;

    public BoardFactory(PacManSprites spriteStore) {
        this.sprites = spriteStore;
    }

    public Board createBoard(Square[][] grid) {
        assert grid != null;

        Board board = new Board(grid);

        int width = board.getWidth();
        int height = board.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Square square = grid[x][y];
                for (Direction dir : Direction.values()) {
                    int dirX = (width + x + dir.getDeltaX()) % width;
                    int dirY = (height + y + dir.getDeltaY()) % height;
                    Square neighbour = grid[dirX][dirY];
                    square.link(neighbour, dir);
                }
            }
        }

        return board;
    }

    public Square createGround() {
        return new Ground(sprites.getGroundSprite());
    }

    public Square createWall() {
        return new Wall(sprites.getWallSprite());
    }

    private static final class Wall extends Square {

        private final Sprite background;

        Wall(Sprite sprite) {
            this.background = sprite;
        }

        @Override
        public boolean isAccessibleTo(Unit unit) {
            return false;
        }

        @Override
        public Sprite getSprite() {
            return background;
        }
    }

    private static final class Ground extends Square {

        private final Sprite background;

        Ground(Sprite sprite) {
            this.background = sprite;
        }

        @Override
        public boolean isAccessibleTo(Unit unit) {
            return true;
        }

        @Override
        public Sprite getSprite() {
            return background;
        }
    }
}
