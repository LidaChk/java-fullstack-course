package org.example.week02.fibonacci;

import org.example.utils.BenchmarkUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class FibonacciBenchmarkTest {

    private final int[] TEST_INPUTS = {10, 20, 30, 35, 40};
    private final Map<String, Map<Integer, Long>> executionTimes = new HashMap<>();
    private final Map<String, Map<Integer, Double>> memoryUsages = new HashMap<>();
    private final LinkedHashMap<String, Function<Integer, Long>> tasks = new LinkedHashMap<>();

    public static void main(String[] args) {
        FibonacciBenchmarkTest benchmark = new FibonacciBenchmarkTest();
        benchmark.runBenchmarks();
    }

    public FibonacciBenchmarkTest() {
        initializeTasks();
        initializeResultMaps();
    }

    private void initializeTasks() {
        tasks.put("recursive", FibonacciRecursive::fibonacciRecursive);
        tasks.put("memoized", FibonacciMemoized::fibonacciMemoized);
        tasks.put("iterative", FibonacciIterative::fibonacciIterative);
    }

    private void initializeResultMaps() {
        for (String key : tasks.keySet()) {
            executionTimes.put(key, new HashMap<>());
            memoryUsages.put(key, new HashMap<>());
        }
    }

    private Stream<Integer> getFibonacciInputs() {
        return Arrays.stream(TEST_INPUTS).boxed();
    }

    public void runBenchmarks() {
        setUp();

        getFibonacciInputs().forEach(n -> {
            memoryBenchmark(n);
            performanceBenchmark(n);
        });

        tearDown();
        
        printPerformanceResults();
        printMemoryUsageResults();
    }

    private void setUp() {
        System.gc();
    }

    private void tearDown() {
        System.gc();
    }

    private void memoryBenchmark(int n) {
        System.gc();
        for (Map.Entry<String, Function<Integer, Long>> task : tasks.entrySet()) {
            String key = task.getKey();
            Function<Integer, Long> func = task.getValue();

            double memory = BenchmarkUtils.measureTaskMemoryUsageMB(() -> func.apply(n));
            memoryUsages.get(key).put(n, memory);
        }
    }

    private void performanceBenchmark(int n) {
        System.gc();
        for (Map.Entry<String, Function<Integer, Long>> task : tasks.entrySet()) {
            String key = task.getKey();
            Function<Integer, Long> func = task.getValue();

            long time = BenchmarkUtils.measureTaskExecutionTimeMS(() -> func.apply(n));
            executionTimes.get(key).put(n, time);
        }
    }

    private <T> void printResults(Map<String, Map<Integer, T>> resultMap, String units) {
        for (int n : TEST_INPUTS) {
            Object[] allArgs = new Object[tasks.size() + 1];

            int i = 0;
            allArgs[i] = n;
            for (Map.Entry<String, Function<Integer, Long>> task : tasks.entrySet()) {
                String key = task.getKey();
                T value = resultMap.get(key).get(n);

                if (value instanceof Double || value instanceof Float) {
                    allArgs[++i] = String.format("%.3f %s", ((Number) value).doubleValue(), units);
                } else {
                    allArgs[++i] = ((Number) value).longValue() + " " + units;
                }
            }
            System.out.printf("%-5d | %-12s | %-12s | %-12s%n", allArgs);
        }
    }

    private void printPerformanceResults() {
        printHeader("⏱️ PERFORMANCE BENCHMARK RESULTS");
        printResults(executionTimes, "ms");
    }

    private void printMemoryUsageResults() {
        printHeader("\n🧠 MEMORY USAGE ANALYSIS");
        printResults(memoryUsages, "mb");
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
}
