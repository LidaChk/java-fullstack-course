package org.example.week01;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import org.example.utils.BenchmarkUtils;
import org.example.utils.BenchmarkUtils.BenchmarkResult;

public class PerformanceBenchmark {

  private static final int BULK_ADDITION_SIZE = 1_000_000;
  private static final int ADD_REMOVE_SIZE = 10_000;

  private static List<Supplier<List<Integer>>> listFactories;

  public static void main(String[] args) {

    System.out.println("=== PERFORMANCE BENCHMARK RESULTS ===\n");

    listFactories = new ArrayList<>();
    listFactories.add(ArrayList::new);
    listFactories.add(LinkedList::new);
    listFactories.add(CustomList::new);

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

      BenchmarkResult result = measureBulkAddition(factory);

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

      BenchmarkResult result = measureAddRemove(factory);

      printResult(listType, result);
    }

    System.out.println();
  }

  private static BenchmarkResult measureBulkAddition(Supplier<List<Integer>> supplier) {
    return BenchmarkUtils.measurePerformance(() -> {
      List<Integer> list = supplier.get();
      for (int i = 0; i < BULK_ADDITION_SIZE; i++) {
        list.add(i);
      }
    });
  }

  private static BenchmarkResult measureAddRemove(Supplier<List<Integer>> supplier) {
    return BenchmarkUtils.measurePerformance(() -> {
      List<Integer> list = supplier.get();
      for (int i = 0; i < ADD_REMOVE_SIZE; i++) {
        list.add(i);
      }
      for (int i = 0; i < ADD_REMOVE_SIZE; i++) {
        if (!list.isEmpty()) {
          list.remove(0);
        }
      }
    });
  }

  private static void printResult(String listType, BenchmarkResult result) {
    System.out.printf("%-12s Time: %6d ms, Memory: %6.2f MB%n",
        listType + ":", result.timeMs(), result.memoryMB());
  }
}
