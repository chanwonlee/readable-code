package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.UserAction;
import cleancode.minesweeper.tobe.position.CellPosition;

public interface InputHandler {

    UserAction getActionFromUser();

    CellPosition getCellPositionFromUser();
}
