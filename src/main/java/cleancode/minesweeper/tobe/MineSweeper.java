package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.game.GameInitializable;
import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;
import cleancode.minesweeper.tobe.position.CellPosition;

public class MineSweeper implements GameRunnable, GameInitializable {

    private final GameBoard gameBoard;
    private final InputHandler inputHandler;
    private final OutputHandler outPutHandler;
    private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public MineSweeper(GameLevel gameLevel, InputHandler inputHandler, OutputHandler outPutHandler) {
        this.gameBoard = new GameBoard(gameLevel);
        this.inputHandler = inputHandler;
        this.outPutHandler = outPutHandler;
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
                String userActionInput = getUserActionInputFromUser();
                actOnCell(cellInput, userActionInput);
            } catch (GameException e) {
                outPutHandler.showExceptionMessage(e);
            } catch (Exception e) {
                outPutHandler.showSimpleMessage("프로그램에 문제가 생겼습니다.");
            }
        }
    }

    private void actOnCell(CellPosition cellPosition, String userActionInput) {
        if (doesUserChooseToPlantFlag(userActionInput)) {
            gameBoard.flagAt(cellPosition);
            checkIfGameIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
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

    private String getUserActionInputFromUser() {
        outPutHandler.showCommentForUserAction();
        return inputHandler.getInput();
    }

    private void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private void changeGameStatusToWin() {
        gameStatus = 1;
    }

    private boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    private boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private void checkIfGameIsOver() {
        if (gameBoard.isAllCellChecked()) {
            changeGameStatusToWin();
        }
    }
}
