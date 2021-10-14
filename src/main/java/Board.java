import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Board extends JPanel {
    private final int numOfRows = 6;
    private final int numOfColumns = 7;
    private final List<Cell> cells = new ArrayList<>();
    private final String[][] cellValuesArray = new String[numOfRows][numOfColumns];
    private boolean isPlayerXTurn = true;
    private final StatusBar statusBar;
    private boolean gameOver = false;

    public Board() {
        this.setSize(675, 675);
        this.setVisible(true);
        this.setLayout(new BorderLayout());

        JPanel jCells = new JPanel();
        jCells.setSize(675, 650);
        jCells.setVisible(true);
        jCells.setLayout(new GridLayout(numOfRows, numOfColumns));
        createCells();
        for (Cell cell: cells) {
            jCells.add(cell);
        }
        this.add(jCells, BorderLayout.CENTER);

        statusBar = new StatusBar(this);
        this.add(statusBar, BorderLayout.SOUTH);

        for (String[] array: cellValuesArray) {
            Arrays.fill(array, " ");
        }
    }

    private void createCells() {
        for (int i = 0; i < numOfRows * numOfColumns; i++) {
            cells.add(new Cell(this, String.valueOf(i)));
        }
    }

    void resetBoard() {
        for (Cell cell: cells) {
            cell.resetCell();
        }
        isPlayerXTurn = true;
        gameOver = false;
        for (String[] array: cellValuesArray) {
            Arrays.fill(array, " ");
        }
    }

    String getElementIfValidMoveOrElseBlank(Cell cell) {
        int column = cells.indexOf(cell) % numOfColumns;
        int row = cells.indexOf(cell) / numOfColumns;
        if (row == numOfRows - 1) {
            return getPlayerTurn();
        } else if (!cellValuesArray[row + 1][column].isBlank()) {
            return getPlayerTurn();
        }
        return " ";
    }

    private String getPlayerTurn() {
        String temp = isPlayerXTurn ? "X" : "O";
        isPlayerXTurn = !isPlayerXTurn;
        return temp;
    }

    void checkStatus(Cell cell) {
        int index = cells.indexOf(cell);
        boolean isBoardFull = true;
        cellValuesArray[index / numOfColumns][index % numOfColumns] = cell.getText();

        for (String[] array: cellValuesArray) {
            for (String s: array) {
                if (s.isBlank()) {
                    isBoardFull = false;
                }
            }
        }

        setStatus(isBoardFull);
    }

    private void setStatus(boolean isBoardFull) {
        statusBar.setStatus(
                checkIfWin("X") ?
                        BoardStatus.X_WINS :
                        checkIfWin("O") ?
                                BoardStatus.O_WINS :
                                isBoardFull ?
                                        BoardStatus.DRAW :
                                        BoardStatus.GAME_IN_PROGRESS);
    }

    private boolean checkIfWin(String element) {
        InARowUtils<String> inARowUtils = new InARowUtils<>(cellValuesArray, element);

        int numInARow = 4;
        gameOver = inARowUtils.checkHorizontal(numInARow)
                || inARowUtils.checkVertical(numInARow)
                || inARowUtils.checkDiagonal(numInARow);

        return gameOver;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
