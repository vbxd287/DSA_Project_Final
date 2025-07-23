package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.Ghost;

public class PlayerCollisions implements CollisionMap {

    @Override
    public void collide(Unit mover, Unit collidedOn) {
        if (mover instanceof Player) {
            playerColliding((Player) mover, collidedOn);
        } else if (mover instanceof Ghost) {
            ghostColliding((Ghost) mover, collidedOn);
        } else if (mover instanceof Pellet) {
            pelletColliding((Pellet) mover, collidedOn);
        }
    }

    private void playerColliding(Player player, Unit collidedOn) {
        if (collidedOn instanceof Ghost) {
            playerVersusGhost(player, (Ghost) collidedOn);
        }
        if (collidedOn instanceof Pellet) {
            playerVersusPellet(player, (Pellet) collidedOn);
        }
    }

    private void ghostColliding(Ghost ghost, Unit collidedOn) {
        if (collidedOn instanceof Player) {
            playerVersusGhost((Player) collidedOn, ghost);
        }
    }

    private void pelletColliding(Pellet pellet, Unit collidedOn) {
        if (collidedOn instanceof Player) {
            playerVersusPellet((Player) collidedOn, pellet);
        }
    }

    public void playerVersusGhost(Player player, Ghost ghost) {
        player.setAlive(false);
    }

    public void playerVersusPellet(Player player, Pellet pellet) {
        pellet.leaveSquare();
        player.addPoints(pellet.getValue());
    }
}
