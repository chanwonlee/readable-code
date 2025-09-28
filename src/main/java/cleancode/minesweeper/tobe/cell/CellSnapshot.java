package cleancode.minesweeper.tobe.cell;

public record CellSnapshot(CellSnapShotStatus status, int nearbyLandMineCount) {

    public static CellSnapshot ofEmpty() {
        return new CellSnapshot(CellSnapShotStatus.EMPTY, 0);
    }

    public static CellSnapshot ofFlag() {
        return new CellSnapshot(CellSnapShotStatus.FLAG, 0);
    }

    public static CellSnapshot ofLandMine() {
        return new CellSnapshot(CellSnapShotStatus.LAND_MINE, 0);
    }

    public static CellSnapshot ofNumber(int nearbyLandMineCount) {
        return new CellSnapshot(CellSnapShotStatus.NUMBER, nearbyLandMineCount);
    }

    public static CellSnapshot ofUnchecked() {
        return new CellSnapshot(CellSnapShotStatus.UNCHECKED, 0);
    }
}
