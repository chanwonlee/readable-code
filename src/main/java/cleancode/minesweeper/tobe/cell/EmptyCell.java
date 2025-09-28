package cleancode.minesweeper.tobe.cell;

public class EmptyCell implements Cell {

    private static final String EMPTY_SIGN = "■";

    private final CellState cellState = CellState.initialize();

    public boolean isLandMine() {
        return false;
    }

    public boolean hasLandMineCount() {
        return false;
    }

    public String getSign() {
        if (cellState.isOpened()) {
            return EMPTY_SIGN;
        }
        if (cellState.isFlagged()) {
            return FLAG_SIGN;
        }
        return UNCHECKED_SIGN;
    }

    @Override
    public void flag() {
        cellState.flag();
    }

    @Override
    public void open() {
        cellState.open();
    }

    @Override
    public boolean isChecked() {
        return cellState.isChecked();
    }

    @Override
    public boolean isOpened() {
        return cellState.isOpened();
    }
}
