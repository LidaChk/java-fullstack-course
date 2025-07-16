package org.example.week02.arrayoperations;

import java.util.Arrays;

public final class ArrayOperations {

  private ArrayOperations() {
  }

  public static void shiftLeftSystemCopy(int[] array, int positions) {
    int n = array.length;
    if (positions < 0 || positions > n) {
      throw new IllegalArgumentException("Positions out of range: " + positions + " must be between 0 and " + n);
    }
    System.arraycopy(array, positions, array, 0, n - positions);
  }

  public static void shiftLeftManualLoop(int[] array, int positions) {
    int n = array.length;
    if (positions < 0 || positions > n) {
      throw new IllegalArgumentException("Positions out of range: " + positions + " must be between 0 and " + n);
    }
    for (int i = 0; i < n - positions; i++) {
      array[i] = array[positions + i];
    }
  }
}
