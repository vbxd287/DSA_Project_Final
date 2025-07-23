package nl.tudelft.jpacman.game;

import java.util.List;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import com.google.common.collect.ImmutableList;

public class SinglePlayerGame extends Game {

    private final Player player;
    private final Level level;

    protected SinglePlayerGame(Player player, Level level) {
        assert player != null;
        assert level != null;
        this.player = player;
        this.level = level;
        this.level.registerPlayer(player);
    }

    @Override
    public List<Player> getPlayers() {
        return ImmutableList.of(player);
    }

    @Override
    public Level getLevel() {
        return level;
    }
}
