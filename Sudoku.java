import java.util.*;

class Sudoku {
  public static void main(String[] args) {
    int[][] board = {
      { 8, 0, 2, 0, 0, 0, 0, 0, 5 },
      { 0, 0, 4, 0, 0, 0, 0, 3, 8 },
      { 5, 0, 0, 9, 0, 0, 2, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 4, 0, 6, 9, 0 },
      { 0, 0, 5, 0, 0, 6, 4, 0, 2 },
      { 0, 0, 0, 0, 2, 9, 0, 6, 0 },
      { 0, 0, 6, 3, 0, 0, 0, 1, 0 },
      { 3, 4, 0, 5, 0, 0, 0, 0, 0 },
    };

    boolean ret = sudoku(board);
    if (ret)
      print(board);
    else
      System.out.println("No solution");
  }

  static void print(int[][] board) {
    for (int i = 0; i < board.length; i++)
      System.out.println(Arrays.toString(board[i]));
  }

  static boolean sudoku(int[][] board) {
    // print(board);

    // find the first unresolved cell that
    // has the least possibilitites.
    int row = board.length;
    int col = board[0].length;
    boolean hasZero = false;
    ArrayList<Integer> first = null;
    int fr = -1;
    int fc = -1;
    for (int i = 0; i < row; i++) {
      for (int j = 0; j  < col; j++) {
        if (board[i][j] == 0) {
          hasZero = true;
          ArrayList<Integer> list = findPossible(board, i, j);
          if (list.size() == 0)
            return false;
          if (first == null || first.size() > list.size()) {
            first = list;
            fr = i;
            fc = j;
          }
        }
      }
    }

    if (first == null)
      return !hasZero;
    
    for (int i = 0; i < first.size(); i++) {
      // System.out.println(first.row+":"+first.col+"->"+first.get(i));
      board[fr][fc] = first.get(i);
      boolean ret = sudoku(board);
      if (ret)
        return ret;
      // System.out.println(first.row+":"+first.col+"->"+0);
      board[fr][fc] = 0;
    }

    return false;
  }

  static ArrayList<Integer> findPossible(int[][] board, int row, int col) {
    ArrayList<Integer> list = new ArrayList<>();
    int bits = 0xFFFF;
    for (int i = 0; i < 9; i++) {
      if (board[row][i] > 0)
        bits &= ~(1 << board[row][i]);
    }
    for (int i = 0; i < 9; i++) {
      if (board[i][col] > 0)
        bits &= ~(1 << board[i][col]);
    }
    int rr = row / 3 * 3;
    int cc = col / 3 * 3;
    for (int i = 0; i<3; i++) {
      for (int j = 0; j<3; j++) {
        int bv = board[rr + i][cc + j];
        if (bv > 0)
        bits &= ~(1 << bv);
      }
    }
    for (int i = 1; i <= 9; i++) {
      if ((bits & (1 << i)) > 0)
        list.add(i);
    }
    return list;
  }
}