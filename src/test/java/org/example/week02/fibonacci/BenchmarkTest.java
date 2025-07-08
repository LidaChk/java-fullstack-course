package org.example.week02.fibonacci;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BenchmarkTest {
    private static final int[] TEST_INPUTS = { 10, 20, 30, 35, 40 };

    private Map<String, Map<Integer, Long>> executionTimes;
    private Map<String, Map<Integer, Long>> memoryUsages;

    private static LinkedHashMap<String, Function<Integer, Long>> tasks;

    @BeforeEach
    void setUp() {

        tasks = new LinkedHashMap<>();
        tasks.put("recursive", FibonacciRecursive::fibonacciRecursive);
        tasks.put("memoized", FibonacciMemoized::fibonacciMemoized);
        tasks.put("iterative", FibonacciIterative::fibonacciIterative);

        executionTimes = new HashMap<>();
        memoryUsages = new HashMap<>();

        for (Map.Entry<String, Function<Integer, Long>> task : tasks.entrySet()) {

            String key = task.getKey();
            executionTimes.put(key, new HashMap<>());
            memoryUsages.put(key, new HashMap<>());
        }

    }

    @AfterEach
    void tearDown() {
        Runtime.getRuntime().gc();
    }

    @Test
    @DisplayName("Performance benchmark for different inputs")
    void performanceBenchmark() {

        for (int n : TEST_INPUTS) {
            for (Map.Entry<String, Function<Integer, Long>> task : tasks.entrySet()) {
                String key = task.getKey();
                Function<Integer, Long> func = task.getValue();

                long time = measureExecutionTime(() -> func.apply(n));
                executionTimes.get(key).put(n, time);

                long memory = measureMemoryUsage(() -> func.apply(n));
                memoryUsages.get(key).put(n, memory);
            }
        }

        printPerformanceResults();
        printMemoryUsageResults();

    }

    private void printResults(Map<String, Map<Integer, Long>> resultMap, String units) {

        for (int n : TEST_INPUTS) {

            Object[] allArgs = new Object[tasks.size() + 1];

            int i = 0;
            allArgs[i] = n;
            for (Map.Entry<String, Function<Integer, Long>> task : tasks.entrySet()) {
                String key = task.getKey();
                allArgs[++i] = resultMap.get(key).get(n) + " " + units;
            }
            System.out.printf("%-5d | %-12s | %-12s | %-12s%n", allArgs);
        }
    }

    private void printPerformanceResults() {
        printHeader("⏱️ PERFORMANCE BENCHMARK RESULTS");
        printResults(executionTimes, "ms");
    }

    void printMemoryUsageResults() {
        printHeader("\n🧠 MEMORY USAGE ANALYSIS");
        printResults(memoryUsages, "KB");
    }

    private void printHeader(String title) {

        System.out.println(title);

        Object[] argsArray = new Object[tasks.size() + 1];
        int i = 0;
        argsArray[i] = "Input";
        for (Map.Entry<String, Function<Integer, Long>> task : tasks.entrySet()) {
            argsArray[++i] = task.getKey();
        }

        System.out.printf("%-5s | %-12s | %-12s | %-12s%n", argsArray);
        System.out.println("------------------------------------------------");
    }

    private long measureExecutionTime(Runnable task) {

        System.gc();

        long startTime = System.currentTimeMillis();
        task.run();
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    private long measureMemoryUsage(Runnable task) {

        // Прогрев
        for (int i = 0; i < 5; i++) {
            task.run();
        }

        System.gc();
        long beforeMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        task.run();

        long afterMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        return (afterMemory - beforeMemory) / 1024;
    }

}
