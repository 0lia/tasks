package task3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sudoku {

    static boolean validateSudoku(List<List<Integer>> sudoku){
        int n = sudoku.size();
        //validate rows
        for (List<Integer> row : sudoku) {
            if (row.stream().distinct().count() < n)
                return false;
        }
        //validate columns
        List<Integer> verticalLine;
        for (int i = 0; i < n; i++) {
            verticalLine = new ArrayList<>();
            for (List<Integer> row : sudoku) {
                verticalLine.add(row.get(i));
            }
            if (verticalLine.stream().distinct().count() < n)
                return false;
        }

        //validate squares
        int sqrtN = (int) Math.sqrt(n);
        List<List<Integer>> squares = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            squares.add(new ArrayList<>());

            for (int row = i/sqrtN * sqrtN; row < i/sqrtN * sqrtN + sqrtN; row++) {
                for (int col = i % sqrtN * sqrtN; col <  i % sqrtN * sqrtN + sqrtN; col++) {
                    squares.get(i).add(sudoku.get(row).get(col));
                }
            }

            if (squares.get(i).stream().distinct().count() < n)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        List<List<Integer>> sudoku = new ArrayList<>();
        sudoku.add(Arrays.asList(7, 8, 4,    1, 5, 9,    3, 2, 6));
        sudoku.add(Arrays.asList(5, 3, 9,    6, 7, 2,    8, 4, 1));
        sudoku.add(Arrays.asList(6, 1, 2,    4, 3, 8,    7, 5, 9));

        sudoku.add(Arrays.asList(9, 2, 8,    7, 1, 5,    4, 6, 3));
        sudoku.add(Arrays.asList(3, 5, 7,    8, 4, 6,    1, 9, 2));
        sudoku.add(Arrays.asList(4, 6, 1,    9, 2, 3,    5, 8, 7));

        sudoku.add(Arrays.asList(8, 7, 6,    3, 9, 4,    2, 1, 5));
        sudoku.add(Arrays.asList(2, 4, 3,    5, 6, 1,    9, 7, 8));
        sudoku.add(Arrays.asList(1, 9, 5,    2, 8, 7,    6, 3, 4));

        System.out.println("Sudoku filled up " + (validateSudoku(sudoku)? "correctly": "with mistakes"));
    }
}
