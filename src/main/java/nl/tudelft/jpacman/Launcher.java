package nl.tudelft.jpacman;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.ui.Action;
import nl.tudelft.jpacman.ui.PacManUI;
import nl.tudelft.jpacman.ui.PacManUiBuilder;

@SuppressWarnings("PMD.TooManyMethods")
public class Launcher {

    private static final PacManSprites SPRITE_STORE = new PacManSprites();
    public static final String DEFAULT_MAP = "/board.txt";
    private String levelMap = DEFAULT_MAP;

    private PacManUI pacManUI;
    private Game game;

    public Game getGame() {
        return game;
    }

    protected String getLevelMap() {
        return levelMap;
    }

    public Launcher withMapFile(String fileName) {
        levelMap = fileName;
        return this;
    }

    public Game makeGame() {
        GameFactory gf = getGameFactory();
        Level level = makeLevel();
        game = gf.createSinglePlayerGame(level);
        return game;
    }

    public Level makeLevel() {
        try {
            return getMapParser().parseMap(getLevelMap());
        } catch (IOException e) {
            throw new PacmanConfigurationException("Unable to create level, name = " + getLevelMap(), e);
        }
    }

    protected MapParser getMapParser() {
        return new MapParser(getLevelFactory(), getBoardFactory());
    }

    protected BoardFactory getBoardFactory() {
        return new BoardFactory(getSpriteStore());
    }

    protected PacManSprites getSpriteStore() {
        return SPRITE_STORE;
    }

    protected LevelFactory getLevelFactory() {
        return new LevelFactory(getSpriteStore(), getGhostFactory());
    }

    protected GhostFactory getGhostFactory() {
        return new GhostFactory(getSpriteStore());
    }

    protected GameFactory getGameFactory() {
        return new GameFactory(getPlayerFactory());
    }

    protected PlayerFactory getPlayerFactory() {
        return new PlayerFactory(getSpriteStore());
    }

    protected void addSinglePlayerKeys(final PacManUiBuilder builder) {
        builder.addKey(KeyEvent.VK_UP, moveTowardsDirection(Direction.NORTH))
            .addKey(KeyEvent.VK_DOWN, moveTowardsDirection(Direction.SOUTH))
            .addKey(KeyEvent.VK_LEFT, moveTowardsDirection(Direction.WEST))
            .addKey(KeyEvent.VK_RIGHT, moveTowardsDirection(Direction.EAST));
    }

    private Action moveTowardsDirection(Direction direction) {
        return () -> {
            assert game != null;
            getGame().move(getSinglePlayer(getGame()), direction);
        };
    }

    private Player getSinglePlayer(final Game game) {
        List<Player> players = game.getPlayers();
        if (players.isEmpty()) {
            throw new IllegalArgumentException("Game has 0 players.");
        }
        return players.get(0);
    }

    public void launch() {
        makeGame();
        PacManUiBuilder builder = new PacManUiBuilder().withDefaultButtons();
        addSinglePlayerKeys(builder);
        pacManUI = builder.build(getGame());
        pacManUI.start();
    }

    public void dispose() {
        assert pacManUI != null;
        pacManUI.dispose();
    }

    public static void main(String[] args) throws IOException {
        new Launcher().launch();
    }
}
