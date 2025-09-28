package cleancode.minesweeper.tobe.position;

import java.util.List;

public record RelativePosition(int deltaRow, int deltaCol) {

    public static final List<RelativePosition> SURROUNDED_POSITIONS = List.of(
            new RelativePosition(-1, -1),
            new RelativePosition(-1, 0),
            new RelativePosition(-1, 1),
            new RelativePosition(0, -1),
            new RelativePosition(0, 1),
            new RelativePosition(1, -1),
            new RelativePosition(1, 0),
            new RelativePosition(1, 1)
    );
}
