package cleancode.minesweeper.tobe.minesweeper.board.position;

import cleancode.minesweeper.tobe.minesweeper.board.cell.Cell;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record CellPositions(List<CellPosition> positions) {

    public static CellPositions from(Cell[][] board) {
        List<CellPosition> cellPositions = new ArrayList<>();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                CellPosition cellPosition = new CellPosition(row, col);
                cellPositions.add(cellPosition);
            }
        }

        return new CellPositions(cellPositions);
    }

    @Override
    public List<CellPosition> positions() {
        return new ArrayList<>(positions);
    }

    public List<CellPosition> subtract(List<CellPosition> positionListToSubtract) {
        ArrayList<CellPosition> cellPositions = new ArrayList<>(positions);
        CellPositions positionsToSubtract = new CellPositions(positionListToSubtract);

        return cellPositions.stream()
                .filter(positionsToSubtract::doesNotContain)
                .toList();

    }

    public List<CellPosition> extractRandomPositions(int count) {
        ArrayList<CellPosition> cellPositions = new ArrayList<>(positions);
        Collections.shuffle(cellPositions);
        return cellPositions.subList(0, count);
    }

    private boolean doesNotContain(CellPosition position) {
        return !positions.contains(position);
    }
}
