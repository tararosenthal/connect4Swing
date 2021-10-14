import java.util.Objects;

public class InARowUtils<T> {
    private final T[][] array;
    private final T element;
    private final int numOfRows;
    private final int numOfColumns;

    public InARowUtils(T[][] array, T element) {
        this.array = array;
        this.element = element;
        this.numOfRows = array.length;
        this.numOfColumns = array[0].length;

        for (T[] innerArray: array) {
            if (innerArray.length != numOfColumns) {
                throw new IndexOutOfBoundsException("2D Array must be rectangular to use InARowUtils");
            }
        }
    }

    public boolean checkHorizontal(int numInARow) {
        for (int row = 0; row < numOfRows; row++) {
            int elementCount = 0;
            for (int column = 0; column < numOfColumns; column++) {
                if (Objects.equals(array[row][column], element)) {
                    elementCount++;
                    if (elementCount >= numInARow) {
                        return true;
                    }
                } else {
                    elementCount = 0;
                }
            }
        }
        return false;
    }

    public boolean checkVertical(int numInARow) {
        for (int column = 0; column < numOfColumns; column++) {
            int elementCount = 0;
            for (int row = 0; row < numOfRows; row++) {
                if (Objects.equals(array[row][column], element)) {
                    elementCount++;
                    if (elementCount >= numInARow) {
                        return true;
                    }
                } else {
                    elementCount = 0;
                }
            }
        }
        return false;
    }

    public boolean checkDiagonal(int numInARow) {
        for (int row = 0; row < numOfRows; row++) {
            if (checkForwardSlash(numInARow, row, 0)) {
                return true;
            }
        }
        for (int column = 0; column < numOfColumns; column++) {
            if (checkForwardSlash(numInARow, 0, column)) {
                return true;
            }
        }
        for (int column = 0; column < numOfColumns; column++) {
            if (checkBackSlash(numInARow, numOfRows - 1, column)) {
                return true;
            }
        }
        for (int row = 0; row < numOfRows; row++) {
            if (checkBackSlash(numInARow, row, numOfColumns - 1)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkForwardSlash(int numInARow, int row,
                                      int column) {
        int leftLimit = Math.min(numOfRows - 1 - row, column);
        int rightLimit = Math.min(row, numOfColumns - 1 - column);
        int maxShift = leftLimit + rightLimit + 1;
        row += leftLimit;
        column -= leftLimit;
        int pieceCount = 0;

        for (int i = 0; i < maxShift; i++) {
            if (Objects.equals(array[row - i][column + i], element)) {
                pieceCount++;
                if (pieceCount >= numInARow) {
                    return true;
                }
            } else {
                pieceCount = 0;
            }
        }
        return false;
    }

    private boolean checkBackSlash(int numInARow, int row,
                                   int column) {
        int plusLimit = Math.min(numOfRows - 1 - row, numOfColumns - 1 - column);
        int minusLimit = Math.min(row, column);
        int maxShift = plusLimit + minusLimit + 1;
        row -= minusLimit;
        column -= minusLimit;
        int pieceCount = 0;

        for (int i = 0; i < maxShift; i++) {
            if (Objects.equals(array[row + i][column + i], element)) {
                pieceCount++;
                if (pieceCount >= numInARow) {
                    return true;
                }
            } else {
                pieceCount = 0;
            }
        }
        return false;
    }

}
