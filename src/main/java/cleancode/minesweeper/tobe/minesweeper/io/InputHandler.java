package cleancode.minesweeper.tobe.minesweeper.io;

import cleancode.minesweeper.tobe.minesweeper.user.UserAction;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;

public interface InputHandler {

    UserAction getActionFromUser();

    CellPosition getCellPositionFromUser();
}
