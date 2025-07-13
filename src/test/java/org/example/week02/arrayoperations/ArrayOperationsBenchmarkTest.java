package org.example.week02.arrayoperations;

import java.util.Arrays;

import org.example.utils.BenchmarkUtils;

class ArrayOperationsBenchmarkTest {

  private static final int[] ARRAY_SIZES = { 1_000, 10_000, 100_000, 1_000_000 };
  private static final int[] SHIFT_POSITIONS = { 1, 10, 100, 1000 };
  private static final int NUM_ITERATIONS = 100;
  private static final String OUTPUT_FORMAT = "%-15s | %-15s | %-25s | %-25s%n";
  private static final String OUTPUT_LINE = "---------------------------------------------------------------------------------";

  public static void main(String[] args) {
    arrayOperationsBenchmarkTest();
  }

  private static int[] createArray(int size) {
    int[] array = new int[size];
    for (int i = 0; i < size; i++) {
      array[i] = i;
    }
    return array;
  }

  static void arrayOperationsBenchmarkTest() {
    System.out.println("\n⏱️ ARRAY SHIFT OPERATIONS BENCHMARK");
    System.out.printf(OUTPUT_FORMAT, "Array Size", "Shift Positions", "System.arraycopy (ms)",
        "Manual Loop (ms)");

    for (int size : ARRAY_SIZES) {
      System.out.println(OUTPUT_LINE);
      for (int positions : SHIFT_POSITIONS) {
        if (positions > size) {
          continue;
        }

        int[] originalArray = createArray(size);
        int[] arrayForSystemCopy = Arrays.copyOf(originalArray, originalArray.length);
        double systemCopyTime = BenchmarkUtils
            .measureTaskExecutionTimeMS(() -> ArrayOperations.shiftLeftSystemCopy(arrayForSystemCopy, positions),
                NUM_ITERATIONS);

        int[] arrayForManualLoop = Arrays.copyOf(originalArray, originalArray.length);
        double manualLoopTime = BenchmarkUtils
            .measureTaskExecutionTimeMS(() -> ArrayOperations.shiftLeftManualLoop(arrayForManualLoop, positions),
                NUM_ITERATIONS);

        System.out.printf(OUTPUT_FORMAT, size, positions, systemCopyTime, manualLoopTime);
      }
    }
    System.out.println(OUTPUT_LINE);
    System.out.println(
        "Analysis: System.arraycopy is generally faster for larger arrays and shifts.");
    System.out
        .println("Manual loop performance degrades with increasing array size and shift positions.");
  }
}
