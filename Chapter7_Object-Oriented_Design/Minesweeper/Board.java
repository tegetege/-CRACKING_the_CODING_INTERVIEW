package Minesweeper;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Board {
    private int nRows;
    private int nColumns;
    private int nBombs = 0;
    private Cell[][] cells;
    private Cell[] bombs;
    private int numUnexposedRemaining; // ボムがなくて安全なセルの数

    // コンストラクタ
    // セルの作成、ボムの配置などを行う
    public Board(int r, int c, int b) {
        nRows = r;
        nColumns = c;
        nBombs = b;

        initializeBoard();
        shuffleBoard();
        setNumberedCells();

        numUnexposedRemaining = nRows * nColumns - nBombs;
    }

    // ボードの初期化
    private void initializeBoard() {
        cells = new Cell[nRows][nColumns];
        bombs = new Cell[nBombs];

        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nColumns; c++) {
                cells[r][c] = new Cell(r, c);
            }
        }

        // ボムのセット
        // 最初からnBombs個を全てボムにする -> その後shuffleBoardで全体をシャッフルする
        for (int i = 0; i < nBombs; i++) {
            int r = i / nColumns;
            int c = (i - r * nColumns) % nColumns;
            bombs[i] = cells[r][c];
            bombs[i].setBomb(true);
        }
    }

    private void shuffleBoard() {
        int nCells = nRows * nColumns;
        Random random = new Random();
        for (int index1 = 0; index1 < nCells; index1++) {
            int index2 = index1 + random.nextInt(nCells - index1);
            if (index1 != index2) {
                /* Get cell at index1. */
                int row1 = index1 / nColumns;
                int column1 = (inde1 - row1 * nColumns) % nColumns;
                Cell cell1 = cells[row1][column1];

                /* Get cell at index2. */
                int row2 = index2 / nColumns;
                int column2 = (index2 - row2 * nColumns) % nColumns;
                Cell cell2 = cells[row2][column2];

                /* Swap. */
                cells[row1][column1] = cell2;
                cell2.setRowAndColumn(row1, column1);
                cells[row2][column2] = cell1;
                cell1.setRowAndColumn(row2, column2);
            }
        }
    }

    private boolean inBounds(int row, int column) {
        return row >= 0 && row < nRows && column >= 0 && column < nColumns;
    }

    /*
    * セルの周りの爆弾の数を設定する.
    * 爆弾はシャッフルされるが、爆弾配列の参照先は同じオブジェクトのまま.
    * */
    private void setNumberedCells(){
        int [] [] deltas = {
                {-1, -1}, {-1, 0}, {-1, 1},
                { 0, -1},          { 0, 1},
                { 1, -1}, { 1, 0}, { 1, 1}
        };
        for (Cell bomb : bombs) {
            int row = bomb.getRow();
            int col = bomb.getColumn();
            for (int[] delta : deltas) {
                int r = row + delta[0];
                int c = col + delta[1];
                if (inBounds(r, c)) {
                    cells[r][c].incrementNumber();
                }
            }
        }
    }

    public void printBoard(boolean showUnderside) {}

    public boolean flipCell(Cell cell) {}

    // ブランクセルを拡張させる
    public void expandBlank(Cell cell) {
        int[][] deltas = {
                {-1, -1}, {-1, 0}, {-1, 1},
                { 0, -1},          { 0, 1},
                { 1, -1}, { 1, 0}, { 1, 1}
        };

        // 再帰を使う代わりにQueueを利用する
        Queue<Cell> toExplore = new LinkedList<Cell>();
        toExplore.add(cell);

        while(!toExplore.isEmpty()) {
            Cell current = toExplore.remove();

            for (int [] delta: deltas) {
                int r = current.getRow() + delta[0];
                int c = current.getColumn() + delta[1];

                if (isBounds(r, c)) {
                    Cell neighbor = cells[r][c];
                    if(flipCell(neighbor) && neighbor.isBlank()) {
                        toExplore.add(neighbor);
                    }
                }
            }
        }
    }

    public UserPlayResult playFlip(UserPlay play) {}

    public Cell getCellAtLocation(UserPlay play) {}

    public int getNumRemaining() { return numUnexposedRemaining; }

}