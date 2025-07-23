package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.PlayerFactory;

public class GameFactory {

    private final PlayerFactory playerFactory;

    public GameFactory(PlayerFactory playerFactory) {
        this.playerFactory = playerFactory;
    }

    public Game createSinglePlayerGame(Level level) {
        return new SinglePlayerGame(playerFactory.createPacMan(), level);
    }

    protected PlayerFactory getPlayerFactory() {
        return playerFactory;
    }
}
