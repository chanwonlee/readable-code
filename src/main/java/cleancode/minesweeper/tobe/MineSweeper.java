package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.config.GameConfig;
import cleancode.minesweeper.tobe.game.GameInitializable;
import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;
import cleancode.minesweeper.tobe.position.CellPosition;

public class MineSweeper implements GameRunnable, GameInitializable {

    private final GameBoard gameBoard;
    private final InputHandler inputHandler;
    private final OutputHandler outPutHandler;
    private GameStatus gameStatus = GameStatus.PLAYING;

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

        while (true) {
            try {
                outPutHandler.showBoard(gameBoard);

                if (doesUserWinTheGame()) {
                    outPutHandler.showGameWinningComment();
                    break;
                }
                if (doesUserLoseTheGame()) {
                    outPutHandler.showGameLosingComment();
                    break;
                }

                CellPosition cellInput = getCellInputFromUser();
                UserAction userAction = getUserActionInputFromUser();
                actOnCell(cellInput, userAction);
            } catch (GameException e) {
                outPutHandler.showExceptionMessage(e);
            }
        }
    }

    private void actOnCell(CellPosition cellPosition, UserAction userAction) {
        if (userAction == UserAction.FLAG) {
            gameBoard.flagAt(cellPosition);
            checkIfGameIsOver();
            return;
        }

        if (userAction == UserAction.OPEN) {
            if (gameBoard.isLandMineCellAt(cellPosition)) {
                gameBoard.openAt(cellPosition);
                changeGameStatusToLose();
                return;
            }

            gameBoard.openSurroundedCells(cellPosition);
            checkIfGameIsOver();
            return;
        }
        outPutHandler.showSimpleMessage("잘못된 번호를 선택하셨습니다.");
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

    private void changeGameStatusToLose() {
        gameStatus = GameStatus.LOSE;
    }

    private void changeGameStatusToWin() {
        gameStatus = GameStatus.WIN;
    }

    private boolean doesUserLoseTheGame() {
        return gameStatus == GameStatus.LOSE;
    }

    private boolean doesUserWinTheGame() {
        return gameStatus == GameStatus.WIN;
    }

    private void checkIfGameIsOver() {
        if (gameBoard.isAllCellChecked()) {
            changeGameStatusToWin();
        }
    }
}
