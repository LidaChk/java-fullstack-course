package org.example.week04.perfomancebenchmark;

import org.example.utils.BenchmarkUtils;
import org.example.utils.BenchmarkUtils.BenchmarkResult;
import org.example.week04.HashMapWithChainingStrategy;
import org.example.week04.linkedhashmapwithchainingstrategy.LinkedHashMapWithChainingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class HashMapPerformanceBenchmark {

    private static final int BULK_PUT_SIZE = 100_000;
    private static final int GET_OPERATIONS_SIZE = 100_000;
    private static final int PUT_REMOVE_SIZE = 10_000;

    private final List<Supplier<Map<Integer, String>>> mapFactories;

    public static void main(String[] args) {
        new HashMapPerformanceBenchmark();
    }

    public HashMapPerformanceBenchmark() {
        System.out.println("=== HASH MAP PERFORMANCE BENCHMARK RESULTS ===\n");

        mapFactories = new ArrayList<>();
        mapFactories.add(HashMap::new);
        mapFactories.add(HashMapWithChainingStrategy::new);
        mapFactories.add(LinkedHashMapWithChainingStrategy::new);

        runBulkPutTest();
        runGetOperationsTest();
        runPutRemoveTest();

        System.out.println("\n=== HASH MAP BENCHMARK COMPLETED ===");
    }

    private Map<Integer, String> createPrepopulatedMap(Supplier<Map<Integer, String>> factory, int size) {
        Map<Integer, String> map = factory.get();
        for (int i = 0; i < size; i++) {
            map.put(i, "value" + i);
        }
        return map;
    }

    private void runBulkPutTest() {
        System.out.println("Bulk Put Test (" + BULK_PUT_SIZE + " elements):");
        System.out.println("----------------------------------------");

        for (Supplier<Map<Integer, String>> factory : mapFactories) {
            BenchmarkResult result = measureBulkPut(factory);
            String mapType = factory.get().getClass().getSimpleName();
            printResult(mapType, result);
        }
        System.out.println();
    }

    private void runGetOperationsTest() {
        System.out.println("Get Operations Test (" + GET_OPERATIONS_SIZE + " get operations):");
        System.out.println("---------------------------------------------------");

        for (Supplier<Map<Integer, String>> factory : mapFactories) {
            Map<Integer, String> populatedMap = createPrepopulatedMap(factory, BULK_PUT_SIZE);
            BenchmarkResult result = measureGetOperations(factory, populatedMap);
            String mapType = factory.get().getClass().getSimpleName();
            printResult(mapType, result);
        }
        System.out.println();
    }

    private void runPutRemoveTest() {
        System.out.println("Put/Remove Test (" + PUT_REMOVE_SIZE + " put + remove operations):");
        System.out.println("---------------------------------------------------");

        for (Supplier<Map<Integer, String>> factory : mapFactories) {
            BenchmarkResult result = measurePutRemove(factory);
            String mapType = factory.get().getClass().getSimpleName();
            printResult(mapType, result);
        }
        System.out.println();
    }

    private static BenchmarkResult measureBulkPut(Supplier<Map<Integer, String>> supplier) {
        return BenchmarkUtils.measurePerformance(() -> {
            Map<Integer, String> map = supplier.get();
            for (int i = 0; i < BULK_PUT_SIZE; i++) {
                map.put(i, "value" + i);
            }
        });
    }

    private static BenchmarkResult measureGetOperations(Supplier<Map<Integer, String>> supplier,
                                                        Map<Integer, String> populatedMap) {
        return BenchmarkUtils.measurePerformance(() -> {
            for (int i = 0; i < GET_OPERATIONS_SIZE; i++) {
                populatedMap.get(i % BULK_PUT_SIZE);
            }
        });
    }

    private static BenchmarkResult measurePutRemove(Supplier<Map<Integer, String>> supplier) {
        return BenchmarkUtils.measurePerformance(() -> {
            Map<Integer, String> map = supplier.get();
            for (int i = 0; i < PUT_REMOVE_SIZE; i++) {
                map.put(i, "value" + i);
            }
            for (int i = 0; i < PUT_REMOVE_SIZE; i++) {
                map.remove(i);
            }
        });
    }

    private static void printResult(String mapType, BenchmarkResult result) {
        System.out.printf("%-35s Time: %6d ms, Memory: %6.2f MB%n",
                mapType, result.timeMs(), result.memoryMB());
    }
}