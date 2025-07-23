package nl.tudelft.jpacman.npc.ghost;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;

public final class Navigation {

    private Navigation() {
    }

    public static List<Direction> shortestPath(Square from, Square to, Unit traveller) {
        if (from.equals(to)) {
            return new ArrayList<>();
        }

        List<Node> targets = new ArrayList<>();
        Set<Square> visited = new HashSet<>();
        targets.add(new Node(null, from, null));
        while (!targets.isEmpty()) {
            Node node = targets.remove(0);
            Square square = node.getSquare();
            if (square.equals(to)) {
                return node.getPath();
            }
            visited.add(square);
            addNewTargets(traveller, targets, visited, node, square);
        }
        return null;
    }

    private static void addNewTargets(Unit traveller, List<Node> targets,
                                      Set<Square> visited, Node node, Square square) {
        for (Direction direction : Direction.values()) {
            Square target = square.getSquareAt(direction);
            if (!visited.contains(target)
                && (traveller == null || target.isAccessibleTo(traveller))) {
                targets.add(new Node(direction, target, node));
            }
        }
    }

    public static Unit findNearest(Class<? extends Unit> type, Square currentLocation) {
        List<Square> toDo = new ArrayList<>();
        Set<Square> visited = new HashSet<>();

        toDo.add(currentLocation);

        while (!toDo.isEmpty()) {
            Square square = toDo.remove(0);
            Unit unit = findUnit(type, square);
            if (unit != null) {
                assert unit.hasSquare();
                return unit;
            }
            visited.add(square);
            for (Direction direction : Direction.values()) {
                Square newTarget = square.getSquareAt(direction);
                if (!visited.contains(newTarget) && !toDo.contains(newTarget)) {
                    toDo.add(newTarget);
                }
            }
        }
        return null;
    }

    public static <T extends Unit> T findUnitInBoard(Class<T> clazz, Board board) {
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                final T ghost = Navigation.findUnit(clazz, board.squareAt(x, y));
                if (ghost != null) {
                    return ghost;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Unit> T findUnit(Class<T> type, Square square) {
        for (Unit unit : square.getOccupants()) {
            if (type.isInstance(unit)) {
                assert unit.hasSquare();
                return (T) unit;
            }
        }
        return null;
    }

    private static final class Node {

        private final Direction direction;
        private final Node parent;
        private final Square square;

        Node(Direction direction, Square square, Node parent) {
            this.direction = direction;
            this.square = square;
            this.parent = parent;
        }

        private Direction getDirection() {
            return direction;
        }

        private Square getSquare() {
            return square;
        }

        private Node getParent() {
            return parent;
        }

        private List<Direction> getPath() {
            if (parent == null) {
                return new ArrayList<>();
            }
            List<Direction> path = parent.getPath();
            path.add(getDirection());
            return path;
        }
    }
}
