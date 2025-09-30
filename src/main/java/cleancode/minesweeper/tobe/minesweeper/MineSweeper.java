package cleancode.minesweeper.tobe.minesweeper;

import cleancode.minesweeper.tobe.minesweeper.board.GameBoard;
import cleancode.minesweeper.tobe.minesweeper.exception.GameException;
import cleancode.minesweeper.tobe.minesweeper.config.GameConfig;
import cleancode.minesweeper.tobe.game.GameInitializable;
import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.minesweeper.io.InputHandler;
import cleancode.minesweeper.tobe.minesweeper.io.OutputHandler;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import cleancode.minesweeper.tobe.minesweeper.user.UserAction;

public class MineSweeper implements GameRunnable, GameInitializable {

    private final GameBoard gameBoard;
    private final InputHandler inputHandler;
    private final OutputHandler outPutHandler;

    public MineSweeper(GameConfig gameConfig) {
        this.gameBoard = new GameBoard(gameConfig.getGameLevel());
        this.inputHandler = gameConfig.getInputHandler();
        this.outPutHandler = gameConfig.getOutputHandler();
    }

    @Override
    public void initialize() {
        gameBoard.initializeGame();
    }

    @Override
    public void run() {
        outPutHandler.showGameStartComments();

        while (gameBoard.isInProgress()) {
            try {
                outPutHandler.showBoard(gameBoard);

                CellPosition cellInput = getCellInputFromUser();
                UserAction userAction = getUserActionInputFromUser();
                actOnCell(cellInput, userAction);
            } catch (GameException e) {
                outPutHandler.showExceptionMessage(e);
            }
        }

        outPutHandler.showBoard(gameBoard);

        if (gameBoard.isWinStatus()) {
            outPutHandler.showGameWinningComment();
        }

        if (gameBoard.isLoseStatus()) {
            outPutHandler.showGameLosingComment();
        }
    }

    private CellPosition getCellInputFromUser() {
        outPutHandler.showCommentForSelectingCell();
        CellPosition cellPosition = inputHandler.getCellPositionFromUser();

        if (gameBoard.isInvalidCellPosition(cellPosition)) {
            throw new GameException("잘못된 좌표를 선택하셨습니다");
        }

        return cellPosition;
    }

    private UserAction getUserActionInputFromUser() {
        outPutHandler.showCommentForUserAction();
        return inputHandler.getActionFromUser();
    }

    private void actOnCell(CellPosition cellPosition, UserAction userAction) {
        if (userAction == UserAction.FLAG) {
            gameBoard.flagAt(cellPosition);
            return;
        }

        if (userAction == UserAction.OPEN) {
            gameBoard.openAt(cellPosition);
            return;
        }

        outPutHandler.showSimpleMessage("잘못된 번호를 선택하셨습니다.");
    }
}
