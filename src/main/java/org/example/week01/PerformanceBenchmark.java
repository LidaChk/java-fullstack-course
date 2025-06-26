package org.example.week01;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class PerformanceBenchmark {

  private static final int BULK_ADDITION_SIZE = 1_000_000;
  private static final int ADD_REMOVE_SIZE = 10_000;

  private static List<Supplier<List<Integer>>> listFactories;

  public static void main(String[] args) {

    System.out.println("=== PERFORMANCE BENCHMARK RESULTS ===\n");

    listFactories = new ArrayList<>();
    listFactories.add(() -> new ArrayList<>());
    listFactories.add(() -> new LinkedList<>());
    listFactories.add(() -> new CustomList<>());


    runBulkAdditionTest();
    runAddRemoveTest();

    System.out.println("\n=== BENCHMARK COMPLETED ===");

  }

  private static void runBulkAdditionTest() {
    System.out.println("Bulk Addition Test (1,000,000 elements):");
    System.out.println("----------------------------------------");

    for (Supplier<List<Integer>> factory : listFactories) {
      List<Integer> list = factory.get();
      String listType = list.getClass().getSimpleName();

      BenchmarkResult result = measureBulkAddition(factory, listType);

      printResult(listType, result);
    }
    System.out.println();
  }

  private static void runAddRemoveTest() {
    System.out.println("Add/Remove Test (10,000 add + 10,000 remove first):");
    System.out.println("---------------------------------------------------");

    for (Supplier<List<Integer>> factory : listFactories) {
      List<Integer> list = factory.get();
      String listType = list.getClass().getSimpleName();

      BenchmarkResult result = measureAddRemove(factory, listType);

      printResult(listType, result);
    }

    System.out.println();
  }

  private static BenchmarkResult measureBulkAddition(Supplier<List<Integer>> supplier, String listType) {
    long memoryBefore = getUsedMemoryBefore();

    long startTime = System.currentTimeMillis();
    List<Integer> list = supplier.get();

    for (int i = 0; i < BULK_ADDITION_SIZE; i++) {
      list.add(i);
    }

    long endTime = System.currentTimeMillis();
    long memoryAfter = getUsedMemoryAfter();

    long memoryMB = bytesToMB(memoryAfter - memoryBefore);

    return new BenchmarkResult(endTime - startTime, memoryMB);
  }

  private static BenchmarkResult measureAddRemove(Supplier<List<Integer>> supplier, String listType) {
    List<Integer> list = supplier.get();

    long memoryBefore = getUsedMemoryBefore();
    long startTime = System.currentTimeMillis();

    for (int i = 0; i < ADD_REMOVE_SIZE; i++) {
      list.add(i);
    }

    for (int i = 0; i < ADD_REMOVE_SIZE; i++) {
      if (!list.isEmpty()) {
        list.remove(0);
      }
    }

    long endTime = System.currentTimeMillis();
    long memoryAfter = getUsedMemoryAfter();

    long memoryMB = bytesToMB(memoryAfter - memoryBefore);

    return new BenchmarkResult(endTime - startTime, memoryMB);
  }

  private static long bytesToMB(long bytes) {
    return bytes / (1024 * 1024);
  }

  private static long getUsedMemoryBefore() {
    Runtime.getRuntime().gc();
    return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
  }

  private static long getUsedMemoryAfter() {
    return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
  }

  private static void printResult(String listType, BenchmarkResult result) {
    System.out.printf("%-12s Time: %6d ms, Memory: %4d MB%n",
        listType + ":", result.getTimeMs(), result.getMemoryMB());
  }

  private static class BenchmarkResult {
    private final long timeMs;
    private final long memoryMB;

    public BenchmarkResult(long timeMs, long memoryMB) {
      this.timeMs = timeMs;
      this.memoryMB = memoryMB;
    }

    public long getTimeMs() {
      return timeMs;
    }

    public long getMemoryMB() {
      return memoryMB;
    }
  }
}
