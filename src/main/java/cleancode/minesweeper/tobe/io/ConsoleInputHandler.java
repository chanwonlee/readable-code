package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.UserAction;
import cleancode.minesweeper.tobe.BoardIndexConverter;
import cleancode.minesweeper.tobe.position.CellPosition;
import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler {

    private static final Scanner SCANNER = new Scanner(System.in);

    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();

    @Override
    public UserAction getActionFromUser() {
        String userInput = SCANNER.nextLine();
        return switch (userInput) {
            case "1" -> UserAction.OPEN;
            case "2" -> UserAction.FLAG;
            default -> UserAction.UNKNOWN;
        };
    }

    @Override
    public CellPosition getCellPositionFromUser() {
        String userInput = SCANNER.nextLine();

        int colIndex = boardIndexConverter.getSelectedColIndex(userInput);
        int rowIndex = boardIndexConverter.getSelectedRowIndex(userInput);
        return new CellPosition(rowIndex, colIndex);
    }
}
