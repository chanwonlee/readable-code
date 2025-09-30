package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapShotStatus;
import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;

public class NumberCellSignProvider implements CellSignProvidable{

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.status() == CellSnapShotStatus.NUMBER;
    }

    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return String.valueOf(cellSnapshot.nearbyLandMineCount());
    }
}
