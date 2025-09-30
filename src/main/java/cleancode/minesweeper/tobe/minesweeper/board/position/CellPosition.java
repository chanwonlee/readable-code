package cleancode.minesweeper.tobe.minesweeper.board.position;

public record CellPosition(int rowIndex, int colIndex) {

    public CellPosition {
        if (rowIndex < 0 || colIndex < 0) {
            throw new IllegalArgumentException("올바르지 않은 좌표입니다");
        }
    }

    public CellPosition calculatePositionBy(RelativePosition relativePosition) {
        if (this.canCalculatePositionBy(relativePosition)) {
            return new CellPosition(
                    this.rowIndex + relativePosition.deltaRow(),
                    this.colIndex + relativePosition.deltaCol()
            );
        }
        throw new IllegalArgumentException("움직일 수 있는 좌표가 아닙니다");
    }

    public boolean canCalculatePositionBy(RelativePosition relativePosition) {
        return this.rowIndex + relativePosition.deltaRow() >= 0
               && this.colIndex + relativePosition.deltaCol() >= 0;
    }

    public boolean isRowIndexLessThan(int rowSize) {
        return this.rowIndex < rowSize;
    }

    public boolean isColIndexLessThan(int colSize) {
        return this.colIndex < colSize;
    }

    public boolean isRowIndexMoreThanOrEqual(int rowSize) {
        return this.rowIndex >= rowSize;
    }

    public boolean isColIndexMoreThanOrEqual(int colSize) {
        return this.colIndex >= colSize;
    }
}
