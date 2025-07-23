package nl.tudelft.jpacman.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class MapParser {

    private final LevelFactory levelCreator;
    private final BoardFactory boardCreator;

    public MapParser(LevelFactory levelFactory, BoardFactory boardFactory) {
        this.levelCreator = levelFactory;
        this.boardCreator = boardFactory;
    }

    public Level parseMap(char[][] map) {
        int width = map.length;
        int height = map[0].length;

        Square[][] grid = new Square[width][height];
        List<Ghost> ghosts = new ArrayList<>();
        List<Square> startPositions = new ArrayList<>();

        makeGrid(map, width, height, grid, ghosts, startPositions);

        Board board = boardCreator.createBoard(grid);
        return levelCreator.createLevel(board, ghosts, startPositions);
    }

    private void makeGrid(char[][] map, int width, int height,
                          Square[][] grid, List<Ghost> ghosts, List<Square> startPositions) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                char c = map[x][y];
                addSquare(grid, ghosts, startPositions, x, y, c);
            }
        }
    }

    protected void addSquare(Square[][] grid, List<Ghost> ghosts,
                             List<Square> startPositions, int x, int y, char c) {
        switch (c) {
            case ' ':
                grid[x][y] = boardCreator.createGround();
                break;
            case '#':
                grid[x][y] = boardCreator.createWall();
                break;
            case '.':
                Square pelletSquare = boardCreator.createGround();
                grid[x][y] = pelletSquare;
                levelCreator.createPellet().occupy(pelletSquare);
                break;
            case 'G':
                Square ghostSquare = makeGhostSquare(ghosts, levelCreator.createGhost());
                grid[x][y] = ghostSquare;
                break;
            case 'P':
                Square playerSquare = boardCreator.createGround();
                grid[x][y] = playerSquare;
                startPositions.add(playerSquare);
                break;
            default:
                throw new PacmanConfigurationException("Invalid character at " + x + "," + y + ": " + c);
        }
    }

    protected Square makeGhostSquare(List<Ghost> ghosts, Ghost ghost) {
        Square ghostSquare = boardCreator.createGround();
        ghosts.add(ghost);
        ghost.occupy(ghostSquare);
        return ghostSquare;
    }

    public Level parseMap(List<String> text) {
        checkMapFormat(text);

        int height = text.size();
        int width = text.get(0).length();

        char[][] map = new char[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = text.get(y).charAt(x);
            }
        }
        return parseMap(map);
    }

    private void checkMapFormat(List<String> text) {
        if (text == null) {
            throw new PacmanConfigurationException("Input text cannot be null.");
        }

        if (text.isEmpty()) {
            throw new PacmanConfigurationException("Input text must consist of at least 1 row.");
        }

        int width = text.get(0).length();

        if (width == 0) {
            throw new PacmanConfigurationException("Input text lines cannot be empty.");
        }

        for (String line : text) {
            if (line.length() != width) {
                throw new PacmanConfigurationException("Input text lines are not of equal width.");
            }
        }
    }

    public Level parseMap(InputStream source) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(source, "UTF-8"))) {
            List<String> lines = new ArrayList<>();
            while (reader.ready()) {
                lines.add(reader.readLine());
            }
            return parseMap(lines);
        }
    }

    @SuppressFBWarnings(value = "OBL_UNSATISFIED_OBLIGATION", justification = "try with resources always cleans up")
    public Level parseMap(String mapName) throws IOException {
        try (InputStream boardStream = MapParser.class.getResourceAsStream(mapName)) {
            if (boardStream == null) {
                throw new PacmanConfigurationException("Could not get resource for: " + mapName);
            }
            return parseMap(boardStream);
        }
    }

    protected BoardFactory getBoardCreator() {
        return boardCreator;
    }
}
